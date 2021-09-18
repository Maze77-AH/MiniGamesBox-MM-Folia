/*
 * Village Defense - Protect villagers from hordes of zombies
 * Copyright (C) 2021  Plugily Projects - maintained by 2Wild4You, Tigerpanzer_02 and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package plugily.projects.minigamesbox.classic.party;

import com.alessiodp.parties.api.Parties;
import com.alessiodp.parties.api.interfaces.PartiesAPI;
import com.alessiodp.parties.api.interfaces.Party;
import com.alessiodp.parties.api.interfaces.PartyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Plajer
 * <p>
 * Created at 09.02.2020
 */
public class PartiesPartyHandlerImpl implements PartyHandler {

  @Override
  public GameParty getParty(Player player) {
    PartiesAPI api = Parties.getApi();
    PartyPlayer partyPlayer = api.getPartyPlayer(player.getUniqueId());

    if (partyPlayer == null)
      return null;

    Party party = api.getParty(partyPlayer.getPartyId());
    if (party == null || party.getMembers().size() <= 1)
      return null;

    Player leader = Bukkit.getPlayer(party.getLeader());
    if (leader == null)
      return null;

    java.util.List<Player> members = party.getOnlineMembers(true).stream()
        .map(localPlayer -> Bukkit.getPlayer(localPlayer.getPlayerUUID()))
        .filter(java.util.Objects::nonNull).collect(java.util.stream.Collectors.toList());

    return new GameParty(members, leader);
  }

  @Override
  public boolean partiesSupported() {
    return true;
  }

  @Override
  public PartyPluginType getPartyPluginType() {
    return PartyPluginType.PARTIES;
  }
}
