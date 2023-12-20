package fr.apptrade.backend.api.v1.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWallet {

    List<Transaction> wallet;

}
