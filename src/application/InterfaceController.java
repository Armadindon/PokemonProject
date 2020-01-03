package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import application.model.appmodel.League;
import application.model.appmodel.TeamBuilder;

public interface InterfaceController {
	void displayUpdate();
	
	void initTeamBuilder(TeamBuilder teamBuilder, Optional<League> league) throws IOException;
}
