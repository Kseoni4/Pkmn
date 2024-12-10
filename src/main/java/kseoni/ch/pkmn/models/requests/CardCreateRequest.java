package kseoni.ch.pkmn.models.requests;

import kseoni.ch.pkmn.models.Card;
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
