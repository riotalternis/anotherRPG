package character;

import java.util.HashMap;

import combat.attacks.Spell;
import combat.attacks.StatusEffect;
import system.Randomizer;


public abstract class Character {
	HashMap<String, Integer> stats = new HashMap<String, Integer>();
	String name = "eric";
	
	
	Character(String name, int hp, int mp, int atk, int def, int sag, int spd) {
		stats.put("hp", hp);
		stats.put("mp", mp);
		stats.put("atk", atk);
		stats.put("def", def);
		stats.put("sag", sag);
		stats.put("lck", 0);
		stats.put("spd", spd);
	}
	
	int currentHP = stats.get("hp");
	int currentMP = stats.get("mp");
	int remainingActions = stats.get("spd");
	
	
	public void restoreAll() {
		this.restoreHP();
		this.restoreMP();
		this.restoreActions();
	}
	
	public void restoreHP() {
		this.currentHP = stats.get("hp");
	}
	
	public void restoreMP() {
		this.currentMP = stats.get("mp");
	}
	
	public void restoreActions() {
		this.remainingActions = stats.get("spd");
	}
	
	class attackResult {
		int result;
		boolean statusAfflicted;
		attackResult(int result, boolean statusAfflicted) {
			this.result = result;
			this.statusAfflicted = statusAfflicted;
		}
	}
	
	public attackResult calculateDamage(Character target, Spell spell) {
		
		attackResult result = new attackResult(-1, false);
		
		if (spell.hpCost > this.currentHP && spell.mpCost >= this.currentMP) {
			if (spell.armorPen) 
			{
				result = new attackResult((int)(spell.multiplier * system.Randomizer.randomFloat(8, 12) * this.stats.get(spell.scaling) + system.Randomizer.randomInt(0, 1)), false);
			}
			else 
			{
				result = new attackResult((int)(spell.multiplier * system.Randomizer.randomFloat(8, 12) * this.stats.get(spell.scaling)/target.stats.get("def") + system.Randomizer.randomInt(0, 1)), false);
			}
			if (Randomizer.randomFloat() <= spell.statusChance) {
				result.statusAfflicted = true;
			}
			this.currentHP -= spell.hpCost;
			this.currentMP -= spell.mpCost;
		}
		return result;
		
	}
	
	public boolean checkDeath() {
		boolean dead = false;
		if (this.currentHP <= 0) {
			dead = true;
		}
		return dead;
		
	}

	
}
