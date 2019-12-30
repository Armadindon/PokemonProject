package application;

import java.io.IOException;

import application.model.appmodel.TeamBuilder;

public interface InterfaceController {
	void displayUpdate();
	
	void initTeamBuilder(TeamBuilder teamBuilder) throws IOException;
}
