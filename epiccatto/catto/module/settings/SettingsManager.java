package epiccatto.catto.module.settings;

import epiccatto.catto.module.Module;

import java.util.ArrayList;

public class SettingsManager {
	private final ArrayList<Setting> settings;
	
	public SettingsManager(){
		this.settings = new ArrayList<>();
	}
	
	public void registerSetting(Setting in) {
		boolean contains = false;
		for (Setting s : this.settings) {
			if (s.getModule().getName().equalsIgnoreCase(in.getModule().getName()) && s.getName().equalsIgnoreCase(in.getName())) {
				contains = true;
			}
		}
		if (!contains) {
			this.settings.add(in);
		}
	}
	
	public ArrayList<Setting> getSettings() {
		return this.settings;
	}
	
	public ArrayList<Setting> getSettingsFromModule(Module mod){
		ArrayList<Setting> out = new ArrayList<>();
		for(Setting s : getSettings()){
			if(s.getModule().equals(mod)){
				out.add(s);
			}
		}
		return out;
	}
	
	public ArrayList<String> getSettingsNameByMod(Module mod){
		ArrayList<String> out = new ArrayList<>();
		for(Setting s : getSettings()){
			if(s.getModule().equals(mod)){
				out.add(s.getName());
			}
		}
		if(out.isEmpty()){
			return null;
		}
		return out;
	}
	
	public Setting getSettingByName(Module mod, String name){
		for(Setting set : getSettings()){
			if(set.getName().equalsIgnoreCase(name) && set.getModule().equals(mod)){
				return set;
			}
		}
		return null;
	}

}