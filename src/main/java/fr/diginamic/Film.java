package fr.diginamic;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="film")
public class Film {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="id_imdb")
	private String idImdb;
	
	private String href;
	private String nom;
	private Integer annee;
	
	@OneToMany(mappedBy="film")
	private Set<Role> roles = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name="film_realisateur", joinColumns = { @JoinColumn(name = "id_film") }, 
    inverseJoinColumns = { @JoinColumn(name = "id_realisateur") } )
	private Set<Realisateur> realisateurs = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name="film_genre", joinColumns = { @JoinColumn(name = "id_film") }, 
	        inverseJoinColumns = { @JoinColumn(name = "id_genre") } )
	private Set<Genre> genres = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name="film_pays", joinColumns = { @JoinColumn(name = "id_film") }, 
	        inverseJoinColumns = { @JoinColumn(name = "id_pays") } )
	private Set<Pays> pays = new HashSet<>();
	
	public Film() {
		
	}
	
	public Film(String nom, Integer annee) {
		super();
		this.nom = nom;
		this.annee = annee;
	}
	
	public boolean acteurExists(Acteur acteur) {
		return roles.stream().anyMatch(r -> r.getActeur().equals(acteur));
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(idImdb).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Film)) {
			return false;
		}
		Film other = (Film) obj;
		return new EqualsBuilder().append(idImdb, other.getIdImdb()).isEquals();
	}

	/** Getter
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}
	/** Setter
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/** Getter
	 * @return the annee
	 */
	public Integer getAnnee() {
		return annee;
	}
	/** Setter
	 * @param annee the annee to set
	 */
	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	@Override
	public String toString() {
		return "id=" + idImdb + ", nom=" + nom + ", annee=" + annee;
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

	/** Setter
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/** Getter
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/** Getter
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/** Setter
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/** Getter
	 * @return the genres
	 */
	public Set<Genre> getGenres() {
		return genres;
	}

	/** Setter
	 * @param genres the genres to set
	 */
	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}

	/** Getter
	 * @return the realisateurs
	 */
	public Set<Realisateur> getRealisateurs() {
		return realisateurs;
	}

	/** Setter
	 * @param realisateurs the realisateurs to set
	 */
	public void setRealisateurs(Set<Realisateur> realisateurs) {
		this.realisateurs = realisateurs;
	}

	/** Getter
	 * @return the pays
	 */
	public Set<Pays> getPays() {
		return pays;
	}

	/** Setter
	 * @param pays the pays to set
	 */
	public void setPays(Set<Pays> pays) {
		this.pays = pays;
	}

	/** Getter pour href
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/** Setter pour href
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	
}