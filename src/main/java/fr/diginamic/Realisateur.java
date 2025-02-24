package fr.diginamic;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="realisateur")
public class Realisateur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="id_imdb")
	private String idImdb;
	private String identite;
	private String href;
	private LocalDate anniversaire;
	
	@ManyToMany
	@JoinTable(name="film_realisateur", joinColumns = { @JoinColumn(name = "id_realisateur") }, 
    inverseJoinColumns = { @JoinColumn(name = "id_film") } )
	private Set<Film> films = new HashSet<>();
	
	public Realisateur() {
		
	}
	
	public Realisateur(String idImdb, String identite, String href) {
		super();
		this.idImdb = idImdb;
		this.identite = identite;
		this.href = href;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(idImdb).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Realisateur)) {
			return false;
		}
		Realisateur autre = (Realisateur)obj;
		return new EqualsBuilder().append(idImdb, autre.getIdImdb()).isEquals();
	}
	
	/** Getter
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/** Setter
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/** Getter
	 * @return the idImdb
	 */
	public String getIdImdb() {
		return idImdb;
	}
	/** Setter
	 * @param idImdb the idImdb to set
	 */
	public void setIdImdb(String idImdb) {
		this.idImdb = idImdb;
	}
	/** Getter
	 * @return the identite
	 */
	public String getIdentite() {
		return identite;
	}
	/** Setter
	 * @param identite the identite to set
	 */
	public void setIdentite(String identite) {
		this.identite = identite;
	}
	/** Getter
	 * @return the href
	 */
	public String getHref() {
		return href;
	}
	/** Setter
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}
	/** Getter
	 * @return the anniversaire
	 */
	public LocalDate getAnniversaire() {
		return anniversaire;
	}
	/** Setter
	 * @param anniversaire the anniversaire to set
	 */
	public void setAnniversaire(LocalDate anniversaire) {
		this.anniversaire = anniversaire;
	}

	/** Getter pour films
	 * @return the films
	 */
	public Set<Film> getFilms() {
		return films;
	}

	/** Setter pour films
	 * @param films the films to set
	 */
	public void setFilms(Set<Film> films) {
		this.films = films;
	}
}