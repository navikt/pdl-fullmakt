package no.nav.pdl.fullmakt.app.fullmakt;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
		import lombok.NoArgsConstructor;
		import org.hibernate.annotations.GenericGenerator;
		import org.hibernate.annotations.Parameter;

import java.util.Date;
import java.util.UUID;
		import javax.persistence.*;

@Entity
@Table(name = "FULLMAKT")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fullmakt {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_fullmakt")
	@GenericGenerator(name = "seq_fullmakt", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {@Parameter(name = "sequence_name", value = "SEQ_FULLMAKT")})
	@Column(name="FULLMAKT_ID", nullable = false, updatable = false, unique = true)
	private Long fullmaktId;


	@Column(name="REGISTRERT",  nullable = false)
	private Date registrert;

	@Column(name="REGISTRERT_AV", length = 20, nullable = false)
	private String registrertAv;

	@Column(name="ENDRET",  nullable = false)
	private Date endret;

	@Column(name="ENDRET_AV", length = 20)
	private String ednretAv;

	@Column(name="OPPHOERT")
	private Boolean opphoert;


	@Column(name="FULLMAKTSGIVER", length = 20,  nullable = false)
	private String fullmaktsgiver;

	@Column(name="FULLMEKTIG", length = 20,  nullable = false)
	private String fullmektig;


	@Column(name="OMRAADE", length = 100,  nullable = false)
	private String omraade;

	@Column(name="GYLDIG_FRA_OG_MED")
	private Date gyldigFraOgMed;

	@Column(name="GYLDIG_TIL_OG_MED")
	private Date gyldigTilOgMed;


}