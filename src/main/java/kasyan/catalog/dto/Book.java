package kasyan.catalog.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.SnowballPorterFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.apache.solr.analysis.StopFilterFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;


@Entity
@Table(name = "books")
@Indexed
@AnalyzerDef(name = "customanalyzer", 
tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
filters={
	@TokenFilterDef(factory = LowerCaseFilterFactory.class),
	@TokenFilterDef(factory = SnowballPorterFilterFactory.class, params={
		@Parameter(name = "language", value = "English"),
	}),
	@TokenFilterDef(factory = StopFilterFactory.class, params={
		@Parameter(name = "words", value = "stoplist.properties")
	})
})
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_book", unique = true, nullable = false)
	private int id;

	@Column(name = "title", unique = true, nullable = false, length = 45)
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String title;

	@Column(name = "short_desc", length = 45)
	private String shortDescription;

	@Column(name = "date_publ", length = 4)
	private String datePublish;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "books_authors", joinColumns = { @JoinColumn(name = "id_book", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "id_author", nullable = false, updatable = false) })
	@IndexedEmbedded
	private List<Author> authors = new ArrayList<Author>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getDatePublish() {
		return datePublish;
	}

	public void setDatePublish(String datePublish) {
		this.datePublish = datePublish;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Book book = (Book) obj;
		List<Author> authors = book.getAuthors();
		if (authors.size() != this.authors.size())
			return false;
		return book.getId() == this.id && this.title.equals(book.getTitle())
				&& this.shortDescription.equals(book.getShortDescription())
				&& this.datePublish.equals(book.getDatePublish())
				&& book.getAuthors().size() == this.authors.size()
				&& this.authors.containsAll(book.getAuthors());
	}

	@Override
	public String toString() {
		return "Book with parameters: id - " + id + ", title - " + title
				+ ",  publsih date - " + datePublish + ", short description - "
				+ shortDescription + ", authors - " + authors;
	}
}
