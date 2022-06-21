package com.alessio.coc;

import com.alessio.coc.models.Clan;
import com.alessio.coc.models.War;

import java.util.ArrayList;

public class ActualGUI {

	private final DataElaboration dataElaboration;
	private Clan clanInfo;
	private ArrayList<War> wars;

	public ActualGUI(Constants constants) {
		ClashOfClansAPI api = new ClashOfClansAPI(constants);
		this.dataElaboration = new DataElaboration(api);

		clanInfo = File.readClanInfoFromFile();
		wars = File.readWarsInfoFromFile();

//		clanInfo = fetchAndSaveClanMembersInfo(coc);
//		wars = fetchAndSaveWarInfo(coc);
	}
}
