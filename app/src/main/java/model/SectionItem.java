package model;

public class SectionItem implements Item{

	private final String title;
	
	public SectionItem(String title) {
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}

	@Override
	public boolean isHeader() {
		return true;
	}

	@Override
	public boolean isContent() {
		return false;
	}

}
