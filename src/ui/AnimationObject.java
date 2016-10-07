package ui;

import game.GameObject;
import game.Position;

public class AnimationObject {

	private AreaDisplayPanel adp;
	private GameObject gameObj;

	private int current = 0;

	private String imgToken;

	private int startX;
	private int startY;
	private int aimX;
	private int aimY;

	private String direction;

	public AnimationObject(AreaDisplayPanel adp, GameObject gameObj, String direction, int startX, int startY, int aimX,
			int aimY) {
		this.adp = adp;
		this.gameObj = gameObj;
		this.direction = direction;
		this.startX = startX;
		this.startY = startY;
		this.aimX = aimX;
		this.aimY = aimY;

		System.out.println("New Animation: Starting at x: " + this.startX + " y: " + this.startY + " Finishing: x: "
				+ this.aimX + " y: " + this.aimY);

	}


	public String getNextImgToken() {
		String token = gameObj.getToken().substring(0, gameObj.getToken().length() - 1) + String.valueOf(current);
		System.out.println("returning: " + token);
		return token;
	}



	public Position nextPosition() {

		int change = 0;

		if (current == 3) {
			return new Position(adp.getRenderOffSetX() + this.startX * adp.tileWidth,
					adp.getRenderOffSetY() + this.startY * adp.tileHeight);
		}

		current++;

		System.out.println("current now = " + this.current);

		if (this.direction.equals("NORTH")) {
			adp.mainPlayerYBuff -= change;
			return new Position(adp.getRenderOffSetX() + this.startX * adp.tileWidth,
					adp.getRenderOffSetY() - 5 + this.startY * adp.tileHeight);
		} else if (this.direction.equals("SOUTH")) {
			adp.mainPlayerYBuff += change;
			return new Position(adp.getRenderOffSetX() + this.startX * adp.tileWidth,
					adp.getRenderOffSetY() + 5 + this.startY * adp.tileHeight);

		} else if (this.direction.equals("EAST")) {
			adp.mainPlayerXBuff += change;
			return new Position(adp.getRenderOffSetX() + 5 + this.startX * adp.tileWidth,
					adp.getRenderOffSetY() + this.startY * adp.tileHeight);
		}

		//adp.mainPlayerXBuff -= change;
		return new Position(adp.getRenderOffSetX() - 5 + this.startX * adp.tileWidth,
				adp.getRenderOffSetY() + this.startY * adp.tileHeight);

	}


	public boolean animationComplete() {
		return current == 3;
	}

	public GameObject getGameObj() {
		return gameObj;
	}

}