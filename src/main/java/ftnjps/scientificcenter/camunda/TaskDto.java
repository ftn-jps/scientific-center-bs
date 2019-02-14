package ftnjps.scientificcenter.camunda;

public class TaskDto {

	private String id;
	private String name;
	private String journalName;
	private String articleName;

	public TaskDto() {}
	public TaskDto(String id, String name, String journalName, String articleName) {
		super();
		this.id = id;
		this.name = name;
		this.journalName = journalName;
		this.articleName = articleName;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJournalName() {
		return journalName;
	}
	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}
	public String getArticleName() {
		return articleName;
	}
	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

}
