/*

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.leapmotion.leap.Vector;

public class saoComponent_old extends JComponent {
	
	private TheHand hand;
	private boolean isDropped;
	private boolean menuCreated = false;
	private ScreenTap tap; 

	public saoComponent_old() {
		for(int orb = 0; orb < 5; orb++) {
			this.orbIconsNormal[orb] = readImage("res/orbs/orb_icons/" + orbNames[orb] + "_normal.png");
			this.orbIconsHoveredAndPressed [orb] = readImage("res/orbs/orb_icons/" + orbNames[orb] + "_hover.png");
		}
	}
	
	public void updateData(TheHand hand, ScreenTap tap, boolean isDropped) {
		this.hand = hand;
		this.isDropped = isDropped;	
		this.tap = tap;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(isDropped && !this.menuCreated) {
			playSound("res/sounds/open.wav");
			this.menuCreated = true;			
			
		} else if(isDropped && this.menuCreated) {
			trackFinger(g2, this.hand.getFrontMostFingerTipPosition());
			if(this.orbTapped != -1 && this.tilePressed == -1) {
				OrbPressedMenu(g2);
				keepSecondaryMenu(g2);
			} else if(this.tilePressed == -1){
				keepMenuOrbs(g2);
			} else if(this.tilePressed != -1) {
				//keepMenuOrbs(g2);  //Add this in for testing purposes <--------------------------- UNCOMMENTERINO
				//System.out.println("Problem is here");
				OrbPressedMenu(g2);
				keepSecondaryMenu(g2);
				tilePressedMenu(g2);
			}
			
		} else if(!isDropped && this.menuCreated) {
			this.menuCreated = false;
			clearScreen(g2);
		}		
    }

	private ImageObserver imgObs;
	private String[] orbNames = {"info", "party", "msg", "plugin", "setting"};
	private Image[] orbIconsNormal = new Image[5];  
	private Image[] orbIconsHoveredAndPressed = new Image[5];
	private boolean[] hoveredOrbs = new boolean[5];
	private int orbHovered = -1;
	private int orbTapped = -1;
	
	private int orbStartX = 350;
	private int yDividingFactor = 16;
	
	private int yFactor = 4;
	private Image overUsed = readImage("res/orbs/backgrounds/btn_normal.png");
	private int xDifference = getImageWidth("res/orbs/backgrounds/btn_normal.png") - getImageWidth("res/orbs/orb_icons/info_normal.png");
	private int yDifference = getImageHeight("res/orbs/backgrounds/btn_normal.png") - getImageHeight("res/orbs/orb_icons/info_normal.png");
	private int pressedDifference = getImageWidth("res/orbs/backgrounds/btn_press_larger.png") - getImageWidth("res/orbs/orb_icons/info_hover.png");
	
	//btn_images are 64x64
	//btn_larger is 67x67
	
	private int orbStartY;
	public void keepMenuOrbs(Graphics2D g2) {
		this.orbStartY = getHeight() / this.yDividingFactor;
		for(int orb = 0; orb < 5; orb++) {         																					// 64                                             64
			if(isContained(this.hand.getFrontMostFingerTipPosition(), this.orbStartX, this.orbStartY * (orb + this.yFactor), this.overUsed.getWidth(this.imgObs), this.orbIconsNormal[orb].getHeight(this.imgObs), "Orb") && this.orbTapped != orb) {
				if(orb != this.orbHovered) {
					playSound("res/sounds/menu.wav");
				}
				this.orbHovered = orb;
				drawTheImage(g2, readImage("res/orbs/backgrounds/btn_hover.png"), this.orbStartX, this.orbStartY * (orb + this.yFactor));
				drawTheImage(g2, this.orbIconsHoveredAndPressed[orb], this.orbStartX + this.xDifference / 2, this.orbStartY * (orb + this.yFactor) + this.yDifference / 2);
			} else if(isPressed(this.hand.getFrontMostFingerTipPosition(), this.orbStartX, this.orbStartY * (orb + this.yFactor), this.overUsed.getWidth(this.imgObs), this.orbIconsNormal[orb].getHeight(this.imgObs), "Orb")) {
				if(orb != this.orbTapped) {
					playSound("res/sounds/press.wav");
					playSound("res/sounds/menu.wav");
					System.out.println("YOU PRESSED A BUTTON");
				}
				this.orbTapped = orb;
				drawTheImage(g2, readImage("res/orbs/backgrounds/btn_press_larger.png"), this.orbStartX, this.orbStartY * (orb + this.yFactor));
				drawTheImage(g2, this.orbIconsHoveredAndPressed[orb], this.orbStartX + this.pressedDifference / 2, this.orbStartY * (orb + this.yFactor) + this.pressedDifference / 2);
			}  else {
				this.hoveredOrbs[orb] = false;
				drawTheImage(g2, readImage("res/orbs/backgrounds/btn_normal.png"), this.orbStartX, this.orbStartY * (orb + this.yFactor));
				drawTheImage(g2, this.orbIconsNormal[orb], this.orbStartX + this.xDifference / 2, this.orbStartY * (orb + this.yFactor) + this.yDifference / 2);
			}
		}
	}
	
	public void OrbPressedMenu(Graphics2D g2) {
		
		this.orbStartY = getHeight() / this.yDividingFactor;
		for(int orb = 0; orb < 5; orb++) {
			if(isContained(this.hand.getFrontMostFingerTipPosition(), this.orbStartX, this.orbStartY * (orb + this.yFactor), this.overUsed.getWidth(this.imgObs), this.orbIconsNormal[orb].getHeight(this.imgObs), "Orb") && this.orbTapped != orb) {
				if(orb != this.orbHovered) {
					playSound("res/sounds/menu.wav");
				}
				this.orbHovered = orb;
				drawTheImage(g2, readImage("res/orbs/backgrounds/btn_hover.png"), this.orbStartX, this.orbStartY * (orb + this.yFactor));
				drawTheImage(g2, this.orbIconsHoveredAndPressed[orb], this.orbStartX + this.xDifference / 2, this.orbStartY * (orb + this.yFactor) + this.yDifference / 2);
							
			} else if(this.orbTapped == orb) {
				drawTheImage(g2, readImage("res/orbs/backgrounds/btn_press_larger.png"), this.orbStartX, this.orbStartY * (orb + this.yFactor));
				drawTheImage(g2, this.orbIconsHoveredAndPressed[orb], this.orbStartX + this.pressedDifference / 2, this.orbStartY * (orb + this.yFactor) + this.pressedDifference / 2);
				//keepSecondaryMenu(g2);//, orb);
			}  else {
				this.hoveredOrbs[orb] = false;
				drawTheImage(g2, readImage("res/orbs/backgrounds/btn_normal.png"), this.orbStartX, this.orbStartY * (orb + this.yFactor));
				drawTheImage(g2, this.orbIconsNormal[orb], this.orbStartX + this.xDifference / 2, this.orbStartY * (orb + this.yFactor) + this.yDifference / 2);
			}
		}

	}
	
	private Font font = new Font("SAO UI 12", Font.BOLD, 15);
	private int[] panelCoords = {this.orbStartX - getImageWidth("res/menu/other/panel.png"), 1};
	private char[] panelTitles = {'I','n','f','o','r','m','a','t','i','o','n'};
	private int[] secondaryMenuItemCounts = {4,4,5,5,5};
	private int yCorrectionFactorForSecondaryMenu = getImageHeight("res/orbs/backgrounds/btn_press_larger.png") / 2 - 215;
	private int yIndicatorCorrector = getImageHeight("res/orbs/backgrounds/btn_press_larger.png") / 2 - getImageHeight("res/menu/other/indicator.png") / 2;
	private int xListCorrector = getImageWidth("res/orbs/backgrounds/btn_press_larger.png") + getImageWidth("res/menu/other/indicator.png");
	private int yListCorrector = 0;
	private int tileWidth = getImageWidth("res/menu/backgrounds/list_normal.png");
	private int tileHeight = getImageHeight("res/menu/backgrounds/list_normal.png");
	private int tileStartY;
	private int tileTLXPoint;
	private int tileTLYPoint;
	private int tileYFactor;
	private int tileHovered = -1;
	private int tilePressed = -1;
	private int fiveTiledCorrection = 10;
	
	
	private void keepSecondaryMenu(Graphics2D g2) { //, int orb) {
		this.tileStartY = this.orbStartY * (this.orbTapped + this.yFactor) + this.yIndicatorCorrector;
		this.tileTLXPoint = this.orbStartX + this.xListCorrector;
		this.tileTLYPoint = this.tileStartY + this.yListCorrector;
		drawLeftSideWindowPane(g2);
		
		drawTheImage(g2,readImage("res/menu/other/indicator.png"),  this.orbStartX + getImageWidth("res/orbs/backgrounds/btn_press_larger.png"), this.tileStartY);
		
		for(int tile = 0; tile < this.secondaryMenuItemCounts[this.orbTapped]; tile++) {
			this.tileYFactor = (readImage("res/menu/backgrounds/list_normal.png").getHeight(this.imgObs) * tile);
			if(this.secondaryMenuItemCounts[this.orbTapped] >= 5) {
				if(isContained(this.hand.getFrontMostFingerTipPosition(), this.tileTLXPoint, this.tileTLYPoint + this.tileYFactor, this.tileWidth, this.tileHeight, "Rect")) {
					if(tile != this.tileHovered) {
						playSound("res/sounds/menu.wav");
					}
					this.tileHovered = tile;
					drawTheImage(g2, readImage("res/menu/backgrounds/list_hover.png"), this.tileTLXPoint, this.tileTLYPoint + this.tileYFactor);
				} else if(isPressed(this.hand.getFrontMostFingerTipPosition(), this.tileTLXPoint, this.tileTLYPoint + this.tileYFactor, this.tileWidth, this.tileHeight, "Rect")) {
					if(tile != this.tilePressed) {
						playSound("res/sounds/press.wav");
					}
					this.tilePressed = tile;
					drawTheImage(g2, readImage("res/menu/backgrounds/list_press.png"), this.tileTLXPoint, this.tileTLYPoint + this.tileYFactor);					
				} else {
					drawTheImage(g2, readImage("res/menu/backgrounds/list_normal.png"), this.tileTLXPoint, this.tileTLYPoint + this.tileYFactor);
				}
			}
		}
	}
	// 4
	//drawTheImage(g2, readImage("res/menu/backgrounds/list_normal.png"), this.tileTLXPoint, this.tileTLYPoint + 20 + this.tileYFactor);
	
	private void tilePressedMenu(Graphics2D g2) {
		this.tileStartY = this.orbStartY * (this.orbTapped + this.yFactor) + this.yIndicatorCorrector;
		this.tileTLXPoint = this.orbStartX + this.xListCorrector;
		this.tileTLYPoint = this.tileStartY + this.yListCorrector;
		drawLeftSideWindowPane(g2);
		
		drawTheImage(g2,readImage("res/menu/other/indicator.png"),  this.orbStartX + getImageWidth("res/orbs/backgrounds/btn_press_larger.png"), this.tileStartY);
		
		 
		for(int tile = 0; tile < this.secondaryMenuItemCounts[this.orbTapped]; tile++) {
			this.tileYFactor = (getImageHeight("res/menu/backgrounds/list_normal.png") * tile);
			if(this.secondaryMenuItemCounts[this.orbTapped] >= 5) {
				if(isContained(this.hand.getFrontMostFingerTipPosition(), this.tileTLXPoint, this.tileTLYPoint + this.tileYFactor, this.tileWidth, this.tileHeight, "Rect")) {
					if(tile != this.tileHovered) {
						playSound("res/sounds/menu.wav");
					}
					this.tileHovered = tile;
					drawTheImage(g2, readImage("res/menu/backgrounds/list_hover.png"), this.tileTLXPoint, this.tileTLYPoint + this.tileYFactor);
				} else if(isPressed(this.hand.getFrontMostFingerTipPosition(), this.tileTLXPoint, this.tileTLYPoint + this.tileYFactor, this.tileWidth, this.tileHeight, "Rect")) {
					if(tile != this.tilePressed) {
						playSound("res/sounds/press.wav");
					}
					this.tilePressed = tile;
					drawTheImage(g2, readImage("res/menu/backgrounds/list_press.png"), this.tileTLXPoint, this.tileTLYPoint + this.tileYFactor);
					keepThirdaryMenu(g2);  // <-------------------------------Commenterino out if no workerino
				} else {
					drawTheImage(g2, readImage("res/menu/backgrounds/list_normal.png"), this.tileTLXPoint, this.tileTLYPoint + this.tileYFactor);
				}
			}
		}
	}
	

	private int[] thirdaryMenuCount = {};
	private void keepThirdaryMenu(Graphics2D g2) {
		drawTheImage(g2,readImage("res/menu/other/indicator.png"),  this.tileTLXPoint + getImageWidth("res/menu/backgrounds/list_hover.png") , this.tileStartY + getImageHeight("res/menu/backgrounds/list_hover.png") - getImageHeight("res/menu/other/indicator.png") / 2);
	}
	
	
	public void drawLeftSideWindowPane(Graphics2D g2) { 
		drawTheImage(g2, readImage("res/menu/other/panel.png"), this.panelCoords[0], this.orbStartY * (this.orbTapped + this.yFactor) + this.yCorrectionFactorForSecondaryMenu);
		g2.setColor(Color.BLACK);
		g2.setFont(this.font);
		
		if(this.orbTapped == 0) {
			g2.drawChars(this.panelTitles, 0, this.panelTitles.length, this.panelCoords[0] + getImageWidth("res/menu/other/info.png") / 2 - 12, this.orbStartY * (this.orbTapped + this.yFactor) + this.yCorrectionFactorForSecondaryMenu + 40);
			drawTheImage(g2, readImage("res/menu/other/info.png"), this.panelCoords[0] + 25, this.orbStartY * (this.orbTapped + this.yFactor) + this.yCorrectionFactorForSecondaryMenu + 65);
		}
	}
	
	
	public int getImageWidth(String url) {
		return readImage(url).getWidth(this.imgObs);
	}
	
	public int getImageHeight(String url) {
		return readImage(url).getHeight(this.imgObs);
	}
	
	public void clearScreen(Graphics2D g2) {
		playSound("res/sounds/menu.wav");
	}
       
	public void trackFinger(Graphics2D g2, Vector tip) {
				g2.setPaint(Color.GREEN);
				g2.drawOval(doMathOnCoordsX(tip.getX()),  doMathOnCoordsY(tip.getY()), 15, 15); //(int) (Math.abs(tip.getZ() - 200) * .2), (int) (Math.abs(tip.getZ() - 200) * .2));
    }
	
	public Image readImage(String url) {
		File file = new File(url);
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void drawTheImage(Graphics2D g2, Image img, int x, int y) {
		g2.drawImage(img, x, y, new Color(0,0,0,0), this.imgObs);
		}
	
	public void playSound(String sound) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(sound).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
	
	//(-140.383, 157.155, 24.5282)
	private int doMathOnCoordsX(double x) {
		return (getWidth() / 2) + (int) x * (getWidth() / 400);
	}
	
	private int doMathOnCoordsY(double y) {
		return getHeight() - (int) y * (getHeight() / 250); 
	}
	
	public boolean isContained(Vector tip, int startX, int startY, int width, int height, String shape) {
		double centerX = startX + (width / 2);
		double centerY = startY + (height / 2);
		double disX = Math.abs(centerX - doMathOnCoordsX(tip.getX()));
		double disY = Math.abs(centerY - doMathOnCoordsY(tip.getY()));
		
		if(shape == "Orb") {
			double rad = width / 2;
			double dis = Math.sqrt(disX * disX + disY * disY);
			if(dis < rad) {
				return true;
			}
		} else if(shape == "Rect") {
			if(disX < width / 2 && disY < height / 2) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isPressed(Vector frontMostFingerTipPosition, int x, int y, int width, int height, String string) {
		if(this.tap != null) {
			if(isContained(this.tap.getPosition(), x, y, width, height, string)) {
				return true;
			}
		}
		return false;
	}
	
}

*/