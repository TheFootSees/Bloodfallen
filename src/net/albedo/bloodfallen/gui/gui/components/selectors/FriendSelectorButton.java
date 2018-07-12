package net.albedo.bloodfallen.gui.gui.components.selectors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.lwjgl.opengl.GL11;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.gui.friends.FriendType;
import net.albedo.bloodfallen.gui.gui.components.buttons.FriendChangerButton;
public class FriendSelectorButton extends SelectorButton{

//	private HashMap<String, ResourceLocation> images = new HashMap();
	private ArrayList<String> loading = new ArrayList();
	private FriendChangerButton changerButton;
	
	public FriendSelectorButton(String text, SelectorSystem system, FriendChangerButton changerButton) {
		super(text, system);
		this.changerButton = changerButton;
	}
	
	@Override
	public void toggle() {
		
		super.toggle();
		
		FriendType type = Albedo.getIrrlicht().getFriendManager().getFriendType(text);
		
		if(type == null){
			changerButton.setIndex(0);
		}else{
			changerButton.setIndex(Arrays.asList(FriendType.values()).indexOf(type));
		}
	}

	@Override
	public String getText() {
//		drawPlayerHead(text, getX() + 1, getY() + 4, 0.3D);
		FriendType type = Albedo.getIrrlicht().getFriendManager().getFriendType(text);
		
		if(type != null){
			return "   " + text + "(" + type.toString() + ")";
		}else{
			return "   " + text;
		}
		
	}
//	
//	 public void drawPlayerHead(String playerName, double x, double y, double size)
//	  {
//	    if (this.images.containsKey(playerName))
//	    {
//	      this.loading.remove(playerName);
//	      if (this.images.get(playerName) != null)
//	      {
//	        GL11.glPushMatrix();
//	        GL11.glScaled(size, size, size);
//	        Wrapper.mc.getTextureManager().bindTexture((ResourceLocation)this.images.get(playerName));
//	        Gui.drawTexturedModalRect((int)x / size, ((int)y - 3.0D) / size, (int)32.0D, (int)32.0D, (int)32.0D, (int)32.0D);
//	        Gui.drawTexturedModalRect((int)x / size, ((int)y - 3.0D) / size, (int)160.0D, (int)32.0D, (int)32.0D, (int)32.0D);
//	        GL11.glPopMatrix();
//	      }
//	    }
//	    else if (!this.loading.contains(playerName))
//	    {
//	      this.loading.add(playerName);
//	      ResourceLocation resourcelocation = new ResourceLocation("images/" + playerName);
//	      ThreadDownloadImageData threaddownloadimagedata = new ThreadDownloadImageData(null, String.format("https://minotar.net/skin" + "/%s.png", new Object[] { StringUtils.stripControlCodes(playerName) }), DefaultPlayerSkin.func_177334_a(getOfflineUUID(playerName)), new ImageBufferDownload());
//	      Wrapper.mc.getTextureManager().loadTexture(resourcelocation, threaddownloadimagedata);
//	      this.images.put(playerName, resourcelocation);
//	    }
//	  }
//	 
//	 public static UUID getOfflineUUID(String username)
//	  {
//	    return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(Charsets.UTF_8));
//	  }
	  
}
