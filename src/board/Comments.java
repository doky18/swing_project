package board;

public class Comments {
	private int comments_idx;
	private String ctitle;
	private String cwriter;
	private String cregdate;

	// 부모의 pk를 보유하는 것도 좋지만, 객체지향적으로 표현한다면 자식이 부모 정보를 보유하는 것이므로, has a 관계를 이용하자
	// 즉, private int news_idx; 라고 해도 되지만
	private News news;

	public int getComments_idx() {
		return comments_idx;
	}

	public void setComments_idx(int comments_idx) {
		this.comments_idx = comments_idx;
	}

	public String getCTitle() {
		return ctitle;
	}

	public void setCTitle(String title) {
		this.ctitle = title;
	}

	public String getCwriter() {
		return cwriter;
	}

	public void setCwriter(String cwriter) {
		this.cwriter = cwriter;
	}

	public String getCregdate() {
		return cregdate;
	}

	public void setCregdate(String cregdate) {
		this.cregdate = cregdate;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

}
