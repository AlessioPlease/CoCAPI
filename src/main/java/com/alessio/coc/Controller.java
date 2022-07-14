package com.alessio.coc;

public class Controller {

	private Model model;
	private View view;

	/**
	 * Initializes the class' {@code Model} and {@code View} objects.
	 *
	 * @param api The {@code ClashOfClansAPI} object.
	 */
	public Controller(ClashOfClansAPI api) {
		this.model = new Model(api);
		this.view = new View(this);
	}

	/**
	 * Calls the {@code Model}'s function {@code searchMemberByName}.
	 *
	 * @param substring The {@code String} object containing the text input
	 *                  from the user.
	 * @return A {@code String} object formatted to be easily readable in the
	 * {@code JLabel} it will be showing in.
	 */
	public String searchMemberByName(String substring) {
		return model.searchMemberByName(substring);
	}
}
