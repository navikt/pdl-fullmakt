package no.nav.pdl.fullmakt.app.fullmakt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullmaktRequest {

	private ListName list;
	private String code;
	private String description;

	public Fullmakt convert() {
		return Fullmakt.builder()
				.list(list)
				.code(code)
				.description(description)
				.build();
	}

	void toUpperCaseAndTrim() {
		setCode(this.code.toUpperCase().trim());
		setDescription(this.description.trim());
	}

}
