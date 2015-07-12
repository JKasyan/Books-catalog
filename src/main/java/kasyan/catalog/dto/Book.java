package kasyan.catalog.dto;

import java.util.HashSet;
import java.util.Set;

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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="books")
@Indexed
public class Book {
	
	public Book(){}
	
	public Book(String title, String shortDescription, String datePublish) {
		this.title = title;
		this.shortDescription = shortDescription;
		this.datePublish = datePublish;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_book", unique=true, nullable=false)
	private int id;
	
	@Column(name="title", unique=true, nullable=false)
	@Field(index=Index.YES, analyze = Analyze.YES, store = Store.NO)
	@NotEmpty
	private String title;
	
	@Column(name="short_desc")
	@Field(index=Index.YES, analyze = Analyze.YES, store = Store.NO)
	@NotNull
	@Max(value = 45)
	@Min(value = 10)
	private String shortDescription;
	
	@NotNull
	@Max(value = 4)
	@Column(name="date_publ")
	private String datePublish;
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="books_authors", joinColumns={
			@JoinColumn(name = "id_book", nullable = false, updatable = false)},
	inverseJoinColumns={@JoinColumn(name = "id_author", nullable = false, updatable = false)})
	@IndexedEmbedded
	private Set<Author> authors = new HashSet<Author>();

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

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
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
		if(this == obj) return true;
		if(obj == null) return false;
		if(this.getClass() != obj.getClass()) return false;
		Book book = (Book) obj;
		Set<Author> authors = book.getAuthors();
		if(authors.size() != this.authors.size()) return false;
		return book.getId() == this.id &&
				this.title.equals(book.getTitle()) &&
				this.shortDescription.equals(book.getShortDescription()) &&
				this.datePublish.equals(book.getDatePublish()) &&
				book.getAuthors().size() == this.authors.size() &&
				this.authors.containsAll(book.getAuthors());
	}
	
}
