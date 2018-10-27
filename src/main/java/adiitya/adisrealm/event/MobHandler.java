package adiitya.adisrealm.event;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import static org.bukkit.entity.EntityType.*;

public final class MobHandler implements Listener {

	@EventHandler
	public void onEndermanGrief(EntityChangeBlockEvent e) {

		EntityType type = e.getEntityType();

		if (type.equals(ENDERMAN))
			e.setCancelled(true);
	}
}
