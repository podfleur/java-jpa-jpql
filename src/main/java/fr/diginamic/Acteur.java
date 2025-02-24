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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "acteur")
public class Acteur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="id_imdb")
	private String idImdb;
	private String identite;
	private String href;
	private LocalDate anniversaire;
	
	@OneToMany(mappedBy = "acteur")
	private Set<Role> roles = new HashSet<>();

	public Acteur() {
		
	}
	
	public Acteur(String idImdb, String identite) {
		super();
		this.idImdb = idImdb;
		this.identite = identite;
	}

	public Acteur(String idImdb, String identite, LocalDate anniversaire) {
		super();
		this.idImdb = idImdb;
		this.identite = identite;
		this.anniversaire = anniversaire;
	}

	public boolean roleExists(Film film) {
		return roles.stream().anyMatch(r -> r.getFilm().equals(film));
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(idImdb).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Acteur)) {
			return false;
		}
		Acteur other = (Acteur) obj;
		return new EqualsBuilder().append(idImdb, other.getIdImdb()).isEquals();
	}

	/**
	 * Getter
	 * 
	 * @return the anniversaire
	 */
	public LocalDate getAnniversaire() {
		return anniversaire;
	}

	/**
	 * Setter
	 * 
	 * @param anniversaire the anniversaire to set
	 */
	public void setAnniversaire(LocalDate anniversaire) {
		this.anniversaire = anniversaire;
	}

	/**
	 * Getter
	 * 
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * Setter
	 * 
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * Getter
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "id=" + idImdb + ", identite=" + identite + ", anniversaire=" + anniversaire;
	}

	public void addRole(Role role) {
		roles.add(role);

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
}