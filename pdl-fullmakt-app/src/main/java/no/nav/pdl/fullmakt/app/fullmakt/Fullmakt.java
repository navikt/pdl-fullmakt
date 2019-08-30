package no.nav.pdl.fullmakt.app.fullmakt;

import lombok.*;
import no.nav.pdl.fullmakt.app.common.auditing.Auditable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FULLMAKT")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(Fullmakt.IdClass.class)
@EqualsAndHashCode(callSuper = false)
public class Fullmakt extends Auditable<String> {

	@Id
	@Column(name = "LIST_NAME")
	@Enumerated(EnumType.STRING)
	private ListName list;

	@Id
	@Column(name = "CODE")
	private String code;

	@Column(name = "DESCRIPTION")
	private String description;

	@Data
	static class IdClass implements Serializable {
		private ListName list;
		private String code;
	}

}
