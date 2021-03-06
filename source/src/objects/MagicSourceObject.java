package objects;

import projectiles.MagicProjectile;
import projectiles.Swing;
import sidescroller.SideScroller;

public class MagicSourceObject extends GameObject {
	
	public MagicSourceObject(SideScroller a) {
		super(a);
		
		type = "OBJECT";
		id = "MAGIC_SOURCE";
		
		//Default image
		image = applet.gameGraphics.get("MAGIC_SOURCE");
		
		//Setup Animation
		animation.frames = applet.gameGraphics.ga(applet.magicSheet, 0,0, 16,16, 80);
		animation.loop = true;
		animation.length = 79;
		animation.rate = 6;
		animation.frame = 0;
		animation.start = 0;
		
		width = 48;
		height = 48;
		
		pos.y = -80;
	}
	
	public void display() {
		applet.image(image, pos.x-applet.originX, pos.y-applet.originY);
	}
	
	public void update() {
		image = animation.animate(applet.frameCount, applet.deltaTime);
		
		//Create new Magic Projectiles
		for(int i=0; i<applet.player.swings.size(); i++) {
			Swing swing = applet.player.swings.get(i);
			
			if(collidesWithSwing(swing)) {
				if(!swing.activated) {
					applet.projectileObjects.add( new MagicProjectile(applet, (int)pos.x, (int)pos.y, swing.direction) );
					
					swing.activated = true;
				}
			}
		}
	}
	
	public boolean collidesWithSwing(Swing swing) {
		return (swing.pos.x-applet.originX+swing.width/2 > pos.x-applet.originX-width/2 && swing.pos.x-applet.originX-swing.width/2 < pos.x-applet.originX+width/2) &&
			   (swing.pos.y-applet.originY+swing.height/2 > pos.y-applet.originY-height/2 && swing.pos.y-applet.originY-swing.height/2 < pos.y-applet.originY+height/2);
	}
	
	public boolean collidesWithPlayer() {
		return (applet.player.pos.x-applet.originX+applet.player.width/2 > pos.x-applet.originX-width/2 && applet.player.pos.x-applet.originX-applet.player.width/2 < pos.x-applet.originX+width/2) &&
			   (applet.player.pos.y-applet.originY+applet.player.height/2 > pos.y-applet.originY-height/2 && applet.player.pos.y-applet.originY-applet.player.height/2 < pos.y-applet.originY+height/2);
	}
}
