/*
 * Copyright (C) Keanu Poeschko - All Rights Reserved
 * Unauthorized copying of this file is strictly prohibited
 *
 * Created by Keanu Poeschko <nur1popcorn@gmail.com>, April 2017
 * This file is part of {Irrlicht}.
 *
 * Do not copy or distribute files of {Irrlicht} without permission of {Keanu Poeschko}
 *
 * Permission to use, copy, modify, and distribute my software for
 * educational, and research purposes, without a signed licensing agreement
 * and for free, is hereby granted, provided that the above copyright notice
 * and this paragraph appear in all copies, modifications, and distributions.
 *
 *
 *
 *
 */

package net.albedo.bloodfallen.launcher;

import com.sun.tools.attach.*;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.launcher.rmi.ILauncher;
import net.albedo.bloodfallen.launcher.rmi.impl.RmiManager;

import java.io.File;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.swing.JOptionPane;

/**
 * The {@link Launcher} is used by the clients launcher.
 *
 * @see LogOutput
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public class Launcher extends Application implements ILauncher
{
    public static final File LAUNCHER_LOC = new File(
                         Launcher.class
                                 .getProtectionDomain()
                                 .getCodeSource()
                                 .getLocation()
                                 .getPath()
                         );

    public Launcher()
    {
        RmiManager.getInstance()
                  .register("Launcher", this);
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        final LogOutput logOutput = new LogOutput();

        final HBox logOutputSettings = new HBox();
        logOutputSettings.setAlignment(Pos.CENTER_RIGHT);
        {
            final ToggleButton showTimestamp = new ToggleButton("Timestamp");
            showTimestamp.setSelected(true);
            logOutput.getShowTimestampProperty().bind(showTimestamp.selectedProperty());

            final ToggleButton showCaller = new ToggleButton("Caller");
            showCaller.setSelected(true);
            logOutput.getShowCallerProperty().bind(showCaller.selectedProperty());

            final ToggleButton showThread = new ToggleButton("Thread");
            showThread.setSelected(true);
            logOutput.getShowThreadProperty().bind(showThread.selectedProperty());

            final ComboBox levelSelector = new ComboBox();
            levelSelector.setMinWidth(100);
            levelSelector.setOnShowing(new EventHandler<Event>() {
                final Set<Level> levels = new HashSet<>();
                @Override
                public void handle(Event event) {
                    logOutput.getLogRecords()
                            .stream()
                            .map(LogRecord::getLevel)
                            .forEach(levels::add);
                    levelSelector.getItems().clear();
                    levelSelector.getItems().addAll(levels);
                }
            });

            levelSelector.setOnAction(event -> logOutput.getFilter().setValue((Level) levelSelector.getSelectionModel().getSelectedItem()));

            final TextField searchBar = new TextField();
            logOutput.getTextFilter().bind(searchBar.textProperty());

            final HBox left = new HBox(new Label("Filter: "), searchBar);
            final HBox right = new HBox(showTimestamp, showCaller, showThread, levelSelector);
            HBox.setHgrow(left, Priority.ALWAYS);
            HBox.setHgrow(right, Priority.ALWAYS);
            left.setAlignment(Pos.CENTER_LEFT);
            right.setAlignment(Pos.CENTER_RIGHT);

            logOutputSettings.getChildren().addAll(left, right);
        }

        final HBox options = new HBox();
        {
            final ComboBox<String> pidSelector = new ComboBox<>();
            pidSelector.setMinWidth(100);

            final Button inject = new Button("Inject");

            final Image icon = new Image("icons/minecraft.png");
            final Map<String, String> vmdMap = new HashMap<>();

            pidSelector.setCellFactory(listView -> new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty)
                {
                    super.updateItem(item, empty);
                    setText(item);
                    setGraphic(null);
                    if(item != null && vmdMap.get(item).contains("net.minecraft.launchwrapper.Launch") | vmdMap.get(item).contains("net.minecraft.client.main.Main"))
//                    if(item != null && vmdMap.get(item).contains("net.minecraft.client.main.Main") | vmdMap.get(item).contains("optifine.OptiFineTweaker") | vmdMap.get(item).contains("net.labymod"))
                    {
                        ImageView imageView = new ImageView(icon);
                        imageView.setFitWidth(10);
                        imageView.setFitHeight(10);
                        imageView.setPreserveRatio(true);
                        setGraphic(imageView);
                    }
                }
            });

            pidSelector.setOnShowing(event -> {
                vmdMap.clear();
                pidSelector.getItems().clear();
                VirtualMachine.list().forEach(vmd -> {
                    vmdMap.put(vmd.id(), vmd.displayName());
                    pidSelector.getItems().add(vmd.id());
                });
                inject.setDisable(true);
            });

            pidSelector.setOnAction(event -> {
                if(pidSelector.getSelectionModel().getSelectedItem() != null)
                    inject.setDisable(false);
            });

            inject.setDisable(true);
            inject.setOnAction(event -> {
                try
                {
                    RmiManager.getInstance()
                              .createRegistry();
                    final String selectedPid = pidSelector.getSelectionModel().getSelectedItem();
                    for(VirtualMachineDescriptor vmd : VirtualMachine.list())
                        if(vmd.id().equals(selectedPid))
                        {
                            inject.setDisable(true);
                            pidSelector.setDisable(true);
                            inject.setText("Attaching..");
                            final VirtualMachine vm = VirtualMachine.attach(vmd);
                            inject.setText("Loading agent..");
                            vm.loadAgent(LAUNCHER_LOC.getAbsolutePath(), "main=" + vmd.displayName().replaceAll(" --", ",").replaceAll(" ", "="));
                            inject.setText("Detaching..");
                            vm.detach();
                         
                            inject.setText("Injected & Detached");
                            return;
                        }
                }
                catch(AlreadyBoundException |
                      RemoteException e)
                {
                    e.printStackTrace();
                    logOutput.log(new LogRecord(Level.SEVERE, "Unable to bind port: " + RmiManager.REGISTRY_PORT));
                }
                catch(AttachNotSupportedException |
                      AgentInitializationException |
                      AgentLoadException |
                      IOException e)
                {
                    e.printStackTrace();
                }
            });

            RmiManager.getInstance()
                      .addShutdownListener(() -> Platform.runLater(() -> {
                          inject.setText("Inject");
                          pidSelector.setDisable(false);
                      }));

            inject.setAlignment(Pos.CENTER);

            final HBox center = new HBox(inject);
            final HBox right = new HBox(new Label("Pid: "), pidSelector);
            HBox.setHgrow(center, Priority.ALWAYS);
            HBox.setHgrow(right, Priority.ALWAYS);
            center.setAlignment(Pos.CENTER_RIGHT);
            right.setAlignment(Pos.CENTER_RIGHT);
            options.getChildren().addAll(center, right);
        }

        options.setAlignment(Pos.CENTER);
        options.setMinHeight(40);

        final VBox layout = new VBox(logOutputSettings, new PerformanceCharts(), logOutput, options);
        VBox.setVgrow(logOutput, Priority.ALWAYS);
        layout.setPadding(new Insets(2, 2, 2, 2));

        final HBox windowOptions = new HBox();
        final VBox rootElement = new VBox(windowOptions, layout);
        rootElement.getStyleClass().add("root-element");

        final Scene scene = new Scene(rootElement, 780, 540);
        {
            final Label title = new Label(Albedo.NAME + " - " + Albedo.VERSION);
            title.setPadding(new Insets(2, 2, 2, 6));
            final Button close = new Button("X");
            close.setOnAction(event -> Platform.exit());

            final HBox left = new HBox(title);
            final HBox right = new HBox(close);
            HBox.setHgrow(left, Priority.ALWAYS);
            HBox.setHgrow(right, Priority.ALWAYS);
            left.setAlignment(Pos.CENTER_LEFT);
            right.setAlignment(Pos.CENTER_RIGHT);

            windowOptions.getStyleClass().add("window-border");

            final EventHandler handler = new EventHandler<MouseEvent>() {
                private double dragX,
                               dragY;

                private boolean dragging;

                @Override
                public void handle(MouseEvent event) {
                    if(event.getEventType() == MouseEvent.MOUSE_DRAGGED)
                    {
                        if(dragging)
                        {
                            scene.getWindow().setX(scene.getWindow().getX() + (event.getScreenX() - dragX));
                            scene.getWindow().setY(scene.getWindow().getY() + (event.getScreenY() - dragY));
                            dragX = event.getScreenX();
                            dragY = event.getScreenY();
                        }
                    }
                    else if(event.getEventType() == MouseEvent.MOUSE_PRESSED)
                    {
                        dragX = event.getScreenX();
                        dragY = event.getScreenY();
                        dragging = true;
                    }
                    else if(event.getEventType() == MouseEvent.MOUSE_RELEASED)
                        dragging = false;
                }
            };

            scene.setOnMouseDragged(handler);
            windowOptions.setOnMousePressed(handler);
            scene.setOnMouseReleased(handler);

            windowOptions.getChildren().addAll(left, right);
        }
        scene.getStylesheets().add("/launcher.css");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
    }

    public static void main(String[] args)
    {
    	
    	JOptionPane.showMessageDialog(null, "This is an UNSTABLE alpha! \n" + "If you want a good experience with this client, please wait for the next version!");
    	System.loadLibrary("attach");
        launch(args);      
    }

    public static void loadDiscordRichPresence() {
    	DiscordRPC lib = DiscordRPC.INSTANCE;
        String applicationId = "455397052457943050";
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        lib.Discord_Initialize(applicationId, handlers, true, steamId);
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
        presence.details = "A Crossover Injection Client!";
        
        lib.Discord_UpdatePresence(presence);
        // in a worker thread
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }, "RPC-Callback-Handler").start();
    }
    
    @Override
    public File getLauncherDir()
    {
        return LAUNCHER_LOC;
    }
}
