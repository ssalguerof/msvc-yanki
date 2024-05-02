package org.bank.ssalguerof.msvc.yanki.customers.models.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bank.ssalguerof.msvc.yanki.customers.documents.Transaccion;
import org.bank.ssalguerof.msvc.yanki.customers.documents.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Yapeo {
    private User userOrigen;
    private User userDestino;
    private Transaccion transaccion;
}
