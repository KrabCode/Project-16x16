package scene.components;

import processing.core.PApplet;
import sidescroller.PClass;
import sidescroller.SideScroller;

public class WorldViewportEditor extends PClass {
	private float sideRight;
	private float sideLeft;
	private float sideUp;
	private float sideDown;
	
	public boolean focus;
	public boolean focused;
	public String focusSide;
	
	public boolean hover;
	public String hoverSide;
	
	public WorldViewportEditor(SideScroller a) {
		super(a);
		
		sideRight = a.width/2;
		sideLeft = -a.width/2;
		sideUp = -a.height/2;
		sideDown = a.height/2;
		
		focusSide = "";
		hoverSide = "";
	}
	
	public void updateEditor() {
		
		//Activate Element
		if(applet.mousePressEvent) {
			
			//Side Up
			if(hover(applet.worldPosition.x-applet.originX, applet.worldPosition.y-applet.originY - applet.worldHeight/2, applet.worldWidth, 10)) {
				focusSide = "UP";
				focus = true;
				focused = true;
			}
			
			//Side Down
			else if(hover(applet.worldPosition.x-applet.originX, applet.worldPosition.y-applet.originY + applet.worldHeight/2, applet.worldWidth, 10)) {
				focusSide = "DOWN";
				focus = true;
				focused = true;
			}
			
			//Side right
			else if(hover(applet.worldPosition.x-applet.originX + applet.worldWidth/2, applet.worldPosition.y-applet.originY, 10,  applet.worldHeight)) {
				focusSide = "RIGHT";
				focus = true;
				focused = true;
			}
			
			//Side Left
			else if(hover(applet.worldPosition.x-applet.originX - applet.worldWidth/2, applet.worldPosition.y-applet.originY, 10,  applet.worldHeight)) {
				focusSide = "LEFT";
				focus = true;
				focused = true;
			}
			else {
				focused = false;
			}
		}
		
		//Hover Element
		if(!applet.mousePressed) {
			
			//Side Up
			if(hover(applet.worldPosition.x-applet.originX, applet.worldPosition.y-applet.originY - applet.worldHeight/2, applet.worldWidth, 10)) {
				hover = true;
				hoverSide = "UP";
			}
			
			//Side Down
			else if(hover(applet.worldPosition.x-applet.originX, applet.worldPosition.y-applet.originY + applet.worldHeight/2, applet.worldWidth, 10)) {
				hover = true;
				hoverSide = "DOWN";
			}
			
			//Side right
			else if(hover(applet.worldPosition.x-applet.originX + applet.worldWidth/2, applet.worldPosition.y-applet.originY, 10,  applet.worldHeight)) {
				hover = true;
				hoverSide = "RIGHT";
			}
			
			//Side Left
			else if(hover(applet.worldPosition.x-applet.originX - applet.worldWidth/2, applet.worldPosition.y-applet.originY, 10,  applet.worldHeight)) {
				hover = true;
				hoverSide = "LEFT";
			}
			else {
				hover = false;
			}
		}
		else {
			hover = false;
		}
		
		//Blur Element
		if(applet.mouseReleaseEvent) {
			focus = false;
			focusSide = "";
		}
		
		//Active Element Actions
		if(focus) {
			switch(focusSide) {
				case "UP": sideUp = applet.mouseY+applet.originY; break;
				case "DOWN": sideDown = applet.mouseY+applet.originY; break;
				case "LEFT": sideLeft = applet.mouseX+applet.originX; break;
				case "RIGHT": sideRight = applet.mouseX+applet.originX; break;
			}
		
			float h = (sideDown-sideUp);
			float w = (sideRight-sideLeft);
			float y = sideUp + h/2;
			float x = sideLeft + w/2;
			
			int pixelOffsetX = 0;
			int pixelOffsetY = 0;
			
			if((PApplet.round( (h)/64)*64)/64 % 2 == 0) {
				pixelOffsetY = 32;
			}
			if((PApplet.round( (w)/64)*64)/64 % 2 == 0) {
				pixelOffsetX = 32;
			}
			
			applet.worldWidth = PApplet.round( (w)/64)*64; //(int)w 
			applet.worldHeight = PApplet.round( (h)/64)*64;
			
			applet.worldPosition.x = PApplet.round( (x)/64)*64+pixelOffsetX;
			applet.worldPosition.y = PApplet.round( (y)/64)*64+pixelOffsetY;
		}
		
		//PApplet.println("-----------------------");
		//PApplet.println((PApplet.round( (w)/64)*64)/64, (PApplet.round( (h)/64)*64)/64);
		//PApplet.println(applet.worldPosition.x, applet.worldPosition.y, applet.worldWidth, applet.worldHeight);
	}
	
	public void displayEditor() {
		
		applet.noStroke();
		applet.fill(0, 100);
		
		//TOP
		applet.quad(
				0,0,
				0, applet.worldPosition.y - applet.worldHeight/2-applet.originY,
				applet.width, applet.worldPosition.y - applet.worldHeight/2-applet.originY,
				applet.width, 0
		);
		
		//BOTTOM
		applet.quad(
				0, applet.height,
				0, applet.worldPosition.y + applet.worldHeight/2-applet.originY,
				applet.width, applet.worldPosition.y + applet.worldHeight/2-applet.originY,
				applet.width, applet.height
		);
		
		//RIGHT
		applet.quad(
				applet.worldPosition.x + applet.worldWidth/2-applet.originX, applet.worldPosition.y - applet.worldHeight/2-applet.originY,
			    applet.width, applet.worldPosition.y - applet.worldHeight/2-applet.originY,
			    applet.width, applet.worldPosition.y + applet.worldHeight/2-applet.originY,
			    applet.worldPosition.x + applet.worldWidth/2-applet.originX, applet.worldPosition.y + applet.worldHeight/2-applet.originY
		);
		
		//LEFT
		applet.quad(
				applet.worldPosition.x - applet.worldWidth/2-applet.originX, applet.worldPosition.y - applet.worldHeight/2-applet.originY,
			    0, applet.worldPosition.y - applet.worldHeight/2-applet.originY,
			    0, applet.worldPosition.y + applet.worldHeight/2-applet.originY,
			    applet.worldPosition.x - applet.worldWidth/2-applet.originX, applet.worldPosition.y + applet.worldHeight/2-applet.originY
		);
		
		applet.noFill();
		applet.strokeWeight(1);
		
		if(focused) {
			applet.stroke(0, 255, 200);
		}
		else {
			applet.stroke(200, 200, 200);
		}
		
		applet.rect(applet.worldPosition.x-applet.originX, applet.worldPosition.y-applet.originY, applet.worldWidth, applet.worldHeight);
		
		if(focus || hover) {
			String side = focus ? focusSide : hoverSide;
			
			switch(side) {
				case "UP":
					if(hover) { applet.stroke(56, 255, 228); }
					else { applet.stroke(0, 246, 255); }
					
					//Line
					applet.line(
						applet.worldPosition.x-applet.originX-applet.worldWidth/2, applet.worldPosition.y-applet.worldHeight/2-applet.originY, 
						applet.worldPosition.x-applet.originX+applet.worldWidth/2, applet.worldPosition.y-applet.worldHeight/2-applet.originY
					);
					
					//Arrow
					applet.line(
						applet.worldPosition.x-applet.originX, applet.worldPosition.y-applet.worldHeight/2-applet.originY, 
						applet.worldPosition.x-applet.originX, applet.worldPosition.y-applet.worldHeight/2-applet.originY-50
					);
					applet.line(
						applet.worldPosition.x-applet.originX, applet.worldPosition.y-applet.worldHeight/2-applet.originY-50, 
						applet.worldPosition.x-applet.originX-20, applet.worldPosition.y-applet.worldHeight/2-applet.originY-50+20
					);
					applet.line(
						applet.worldPosition.x-applet.originX, applet.worldPosition.y-applet.worldHeight/2-applet.originY-50, 
						applet.worldPosition.x-applet.originX+20, applet.worldPosition.y-applet.worldHeight/2-applet.originY-50+20
					);
					break;
					
				case "DOWN":
					if(hover) { applet.stroke(56, 255, 228); }
					else { applet.stroke(0, 246, 255); }
					
					//Line
					applet.line(
						applet.worldPosition.x-applet.originX-applet.worldWidth/2, applet.worldPosition.y+applet.worldHeight/2-applet.originY, 
						applet.worldPosition.x-applet.originX+applet.worldWidth/2, applet.worldPosition.y+applet.worldHeight/2-applet.originY
					);
					
					//Arrow
					applet.line(
						applet.worldPosition.x-applet.originX, applet.worldPosition.y+applet.worldHeight/2-applet.originY, 
						applet.worldPosition.x-applet.originX, applet.worldPosition.y+applet.worldHeight/2-applet.originY+50
					);
					applet.line(
						applet.worldPosition.x-applet.originX, applet.worldPosition.y+applet.worldHeight/2-applet.originY+50, 
						applet.worldPosition.x-applet.originX-20, applet.worldPosition.y+applet.worldHeight/2-applet.originY+50-20
					);
					applet.line(
						applet.worldPosition.x-applet.originX, applet.worldPosition.y+applet.worldHeight/2-applet.originY+50, 
						applet.worldPosition.x-applet.originX+20, applet.worldPosition.y+applet.worldHeight/2-applet.originY+50-20
					);
					break;
					
				case "LEFT":
					if(hover) { applet.stroke(56, 255, 228); }
					else { applet.stroke(0, 246, 255); }
					
					//Line
					applet.line(
						applet.worldPosition.x-applet.originX-applet.worldWidth/2, applet.worldPosition.y-applet.worldHeight/2-applet.originY, 
						applet.worldPosition.x-applet.originX-applet.worldWidth/2, applet.worldPosition.y+applet.worldHeight/2-applet.originY
					);
					
					//Arrow
					applet.line(
						applet.worldPosition.x-applet.worldWidth/2-applet.originX, applet.worldPosition.y-applet.originY, 
						applet.worldPosition.x-applet.worldWidth/2-applet.originX-50, applet.worldPosition.y-applet.originY
					);
					applet.line(
						applet.worldPosition.x-applet.worldWidth/2-applet.originX-50, applet.worldPosition.y-applet.originY, 
						applet.worldPosition.x-applet.worldWidth/2-applet.originX-50+20, applet.worldPosition.y-applet.originY-20
					);
					applet.line(
						applet.worldPosition.x-applet.worldWidth/2-applet.originX-50, applet.worldPosition.y-applet.originY, 
						applet.worldPosition.x-applet.worldWidth/2-applet.originX-50+20, applet.worldPosition.y-applet.originY+20
					);
					break;
				case "RIGHT":
					if(hover) { applet.stroke(56, 255, 228); }
					else { applet.stroke(0, 246, 255); }
					
					//Line
					applet.line(
						applet.worldPosition.x-applet.originX+applet.worldWidth/2, applet.worldPosition.y-applet.worldHeight/2-applet.originY, 
						applet.worldPosition.x-applet.originX+applet.worldWidth/2, applet.worldPosition.y+applet.worldHeight/2-applet.originY
					);
					
					//Arrow
					applet.line(
						applet.worldPosition.x+applet.worldWidth/2-applet.originX, applet.worldPosition.y-applet.originY, 
						applet.worldPosition.x+applet.worldWidth/2-applet.originX+50, applet.worldPosition.y-applet.originY
					);
					applet.line(
						applet.worldPosition.x+applet.worldWidth/2-applet.originX+50, applet.worldPosition.y-applet.originY, 
						applet.worldPosition.x+applet.worldWidth/2-applet.originX+50-20, applet.worldPosition.y-applet.originY-20
					);
					applet.line(
						applet.worldPosition.x+applet.worldWidth/2-applet.originX+50, applet.worldPosition.y-applet.originY, 
						applet.worldPosition.x+applet.worldWidth/2-applet.originX+50-20, applet.worldPosition.y-applet.originY+20
					);
					break;
			}
		}
	}
	
	public void setSize() {
		sideRight = applet.worldPosition.x + applet.worldWidth/2;
		sideLeft = applet.worldPosition.x - applet.worldWidth/2;
		sideUp = applet.worldPosition.y - applet.worldHeight/2;
		sideDown = applet.worldPosition.y + applet.worldHeight/2;
	}
	
	private boolean hover(float x, float y, float w, float h) {
		return(applet.mouseX > x-w/2 && applet.mouseX < x+w/2 && applet.mouseY > y-h/2 && applet.mouseY < y+h/2);
	}
}
