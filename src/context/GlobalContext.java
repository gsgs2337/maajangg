package context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedTransferQueue;

import data.ActionData;
import player.Player;

public class GlobalContext {
	
	private static final GlobalContext instance = new GlobalContext();

	/**	master専用queue */
	private LinkedTransferQueue<ActionData> masterQueue = new LinkedTransferQueue<ActionData>();
	/**	player専用queue */
	private Map<Player, LinkedTransferQueue<ActionData>> queueMap = new ConcurrentHashMap<Player, LinkedTransferQueue<ActionData>>();
	
	private GlobalContext() {
	}
	
	public static GlobalContext getInstance() {
		return instance;
	}

	public LinkedTransferQueue<ActionData> getMasterQueue() {
		return this.masterQueue;
	}
	
	public void createGamePlayerQueue(Player player) {
		if(this.queueMap.get(player) == null) {
			this.queueMap.put(player, new LinkedTransferQueue<ActionData>());
		}
	}
	
	public LinkedTransferQueue<ActionData> getGamePlayerQueue(Player player){
		if(this.queueMap.get(player) == null) {
			return null;
		}else {
			return this.queueMap.get(player);
		}
	}
	
}
