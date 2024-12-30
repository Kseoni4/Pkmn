package kseoni.ch.pkmn.shared.models.requests;

import kseoni.ch.pkmn.shared.models.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardCreateRequest {

    private Card baseCard;

}
