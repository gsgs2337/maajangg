package data;

import enums.ActionType;
import pai.Pai;
import player.Player;

public class ActionData {

	private Player triggerPlayer;
	private ActionType actionType;
	private Pai pai;
	
	//引数なしコンストラクタは
	public ActionData(Player triggerPlayer, ActionType actionType, Pai pai) {
		super();
		this.triggerPlayer = triggerPlayer;
		this.actionType = actionType;
		this.pai = pai;
	}



	public Player getTriggerPlayer() {
		return triggerPlayer;
	}

	public void setTriggerPlayer(Player triggerPlayer) {
		this.triggerPlayer = triggerPlayer;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public Pai getPai() {
		return pai;
	}

	public void setPai(Pai pai) {
		this.pai = pai;
	}
	

}
