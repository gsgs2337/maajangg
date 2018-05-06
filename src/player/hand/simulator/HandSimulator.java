package player.hand.simulator;

import java.util.ArrayList;
import java.util.List;

import data.ActionData;
import enums.PaiType;
import pai.Pai;
import pai.PaiSet;
import player.Player;
import player.hand.PossibleGoalHand;
//handCheckFacade?
public class HandSimulator {

	public HandSimulator() {
		super();
	}
	
	/**
	 * 他playerの捨牌に対し、チー/ポン/カンができるかを確認。
	 * @param player
	 * @param data
	 */
	public void simulate(Player player, ActionData data) {
		//捨てた人の次の人しか、チーできない。
		if(data.getTriggerPlayer().getNextPlayer().equals(player)) {
			player.setCanChi(checkChi(player.getHandList(),data.getPai()));
		}
		player.setCanPon(checkPon(player.getHandList(),data.getPai()));
		player.setCanMinKan(checkMinKan(player.getHandList(),data.getPai()));
		//checkRon(player.getHandList(),pai);
	}
	
	public void simulate(Player player) {
		//暗槓
		List<Pai> ankanList = checkAnkan(player);
		if(ankanList != null) {
			for(Pai p : ankanList) {
				System.out.printf("%s can do ANKAN:\n", player, p);
			}
		}	
		//ツモ
		checkTsumo(player);
	}
	
	
	private boolean checkChi(List<Pai> origHandList, Pai tartgetPai) {
		//字牌は対象外
		if(tartgetPai.getPaiType() == PaiType.CHARACTER) {
			return false;
		}
		
		//チーの可能性のある牌の数字。隣 or 2つ隣
		int nextNum=0, smallNum = 0, bigNum = 0;
		int targetPaiNum = tartgetPai.getNumberType().getIndex();
		
		for(Pai p : origHandList) {
			//違う色の牌は対象外
			if(p.getPaiType() != tartgetPai.getPaiType()) {
				continue;
			}
			int pNum = p.getNumberType().getIndex();
			//同じ数字は対象外
			if(pNum == targetPaiNum) {
				continue;
			}
			
			if( pNum + 2 == targetPaiNum) {
				smallNum = pNum;
			}else if( pNum - 2 == targetPaiNum) {
				bigNum = pNum;
			}else if( pNum + 1 == targetPaiNum 
					|| pNum - 1 == targetPaiNum) {
				nextNum = pNum;
			}
			//ex. 引いた牌 or 誰かが捨てた牌が「4」
			//2つ小さい牌が存在する場合（ex. 2が既にあるとわかっている場合. 3があれば良い）
			if(smallNum > 0 && ++pNum == targetPaiNum) {
				return true;
			}
			//2つ大きい牌が存在する場合（ex. 6が既にあるとわかっている場合. 5があれば良い）
			if(bigNum > 0 && --pNum == targetPaiNum) {
				return true;
			}
			//隣の牌が存在する場合（ex. 3 or 5 が既にあるとわかっている場合）
			if(nextNum > 0) {
				if(++pNum == targetPaiNum 
						|| ++pNum == nextNum
						|| --pNum == targetPaiNum
						|| --pNum == nextNum) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkPon(List<Pai> origHandList, Pai tartgetPai) {
		return checkPonOrKan(origHandList, tartgetPai, 2);
	}

	private boolean checkMinKan(List<Pai> origHandList, Pai tartgetPai) {
		return checkPonOrKan(origHandList, tartgetPai, 3);
	}

	private void checkRon(List<Pai> origHandList, Pai tartgetPai) {
		
	}
	private void checkTsumo(Player player) {
		//この時の手牌はツモも含めた状態
		List<Pai> origHandList = player.getHandList();
		//アタマのList
		List<Pai[]> headArrayList = createHeadArrayList(origHandList);
		//上がりパターンのチェック
		if(headArrayList.isEmpty()) {
			//アタマがないので、順子のペアを探す（刻子はない）
		}else {
			//アタマがある
			//(1)順子→刻子の順に探す
			//(2)刻子→順子の順位探す
			//それぞれアタマが違うパターンをなめていく
			for(Pai[] headArray: headArrayList) {
				createPossibleGoalHandFromShuntsu(headArray, origHandList);
			}
		}		
	}
	
	private List<Pai[]> createHeadArrayList(List<Pai> list) {
		List<Pai[]> headList = new ArrayList<Pai[]>();
		bigloop: for(Pai pai:list) {
			//既にアタマのListに追加されている牌は除外
			if(!headList.isEmpty()) {
				for(Pai[] heads : headList) {
					if(heads[0].equals(pai)){
						continue bigloop;						
					}
				}
			}

			for(Pai p:list) {
				//完全に同じ牌は除外
				if(pai == p) {
					continue;
				}
				//重複する牌があった場合は、アタマのListに追加
				if(pai.equals(p)) {
					Pai[] headArray = new Pai[] {pai, p};
					headList.add(headArray);
					break;
				}
			}
		}
		return headList;	
	}
	
	private void createPossibleGoalHandFromShuntsu(Pai[] headArray, List<Pai> origHandList) {
		int headCnt = 0;
		PaiSet paiSet;
		List<Pai> mansList = new ArrayList<Pai>();
		List<Pai> sousList = new ArrayList<Pai>();
		List<Pai> pinsList = new ArrayList<Pai>();
		List<Pai> ChrsList = new ArrayList<Pai>();
		
		for(Pai p:origHandList) {
			//アタマ2つ分は無視
			if(headArray[0].equals(p) && headCnt < 2) {
				++headCnt;
				continue;
			}
			switch(p.getPaiType()) {
			case CHARACTER:
				ChrsList.add(p);
				break;
			case MAN:
				mansList.add(p);
				break;
			case PIN:
				pinsList.add(p);
				break;
			case SOU:
				sousList.add(p);
				break;
				default:
					break;
			}
		}
		
		PossibleGoalHand goalHand = new PossibleGoalHand();
		//(1)このループで、順子をgoalHandに抽出。
		for(Pai p:mansList) {
			int pNum = p.getNumberType().getIndex();
			Pai nextPai = null;
			
			for(Pai pai: mansList) {
				//自分も含め、同じ牌は無視
				if(pai.equals(p)) {
					continue;					
				}
				if(nextPai != null && 
					nextPai.getNumberType().getIndex() + 2 == pai.getNumberType().getIndex()) {
					new PaiSet(p, nextPai, pai);
					//goalHand.add(new PaiSet(p, nextPai, pai));
					mansList.remove(p);
					mansList.remove(nextPai);
					mansList.remove(pai);
					break;
				}
				if(pNum + 1 == pai.getNumberType().getIndex()) {
					nextPai = pai;
				}
			}
		}
		//(2)次のループで、刻子をgoalHandに抽出
		for(Pai p: mansList) {
			Pai samePai= null;
			for(Pai pai : mansList) {
				//自分以外の同じ牌をカウント
				if(p == pai) {
					continue;
				}
				if(p.equals(pai)) {
					if(samePai == null) {
						samePai = pai;						
					}else {
						new PaiSet(p, samePai, pai);
						//goalHand.add(new PaiSet(p, nextPai, pai));
						mansList.remove(p);
						mansList.remove(samePai);
						mansList.remove(pai);
					}
				}
			}
		}
		//(3)「惜しい」組を抽出（3,4とか、6,6とか、1,3とか？）
		//(4)ここでListに残ってる奴らは、完全に浮いてる奴。
	}
	
	

	private boolean checkPonOrKan(List<Pai> origHandList, Pai tartgetPai, int ponOrKan) {
		int samePaiCnt = 0;
		for(Pai p : origHandList) {
			//違う色の牌は対象外
			if(p.getPaiType() != tartgetPai.getPaiType()) {
				continue;
			}
			switch(tartgetPai.getPaiType()) {
			case CHARACTER:
				if(p.getCharType() == tartgetPai.getCharType()) {
					++samePaiCnt;
				}
				break;
			case MAN:
			case PIN:
			case SOU:
				if(p.getNumberType() == tartgetPai.getNumberType()) {
					++samePaiCnt;
				}
				break;
				default:
					break;
			}
			if(samePaiCnt == ponOrKan) {
				return true;
			}
		}
		return false;
	}

	private List<Pai> checkAnkan(Player player) {
		int samePaiCnt = 0;
		List<Pai> ankanList = null;
		for(Pai pai : player.getHandList()) {
			for(Pai p : player.getHandList()) {
				//違う色の牌は対象外
				if(p.getPaiType() != pai.getPaiType()) {
					continue;
				}
				switch(pai.getPaiType()) {
				case CHARACTER:
					if(p.getCharType() == pai.getCharType()) {
						++samePaiCnt;
					}
					break;
				case MAN:
				case PIN:
				case SOU:
					if(p.getNumberType() == pai.getNumberType()) {
						++samePaiCnt;
					}
					break;
					default:
						break;
				}
				if(samePaiCnt == 4) {
					if(ankanList == null) {
						ankanList = new ArrayList<Pai>();
					}
					ankanList.add(p);
				}
			}
		}
		return ankanList;
	}
}
