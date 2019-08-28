// AuthorizationProviderStub.cs
// Copyright © 2019 NextStep IT Training. All rights reserved.
//

package com.tc3.tc3service.doubles;

import com.tc3.tc3service.models.CardInfo;
import com.tc3.tc3service.services.IAuthorizationProvider;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class AuthorizationProviderStub implements IAuthorizationProvider {

    public String authorize(BigDecimal amount, CardInfo cardInfo) {

        String result = null;
        Random random = new Random();

        // The point of this mock is to return an authorization for one CardInfo, and no authorization
        // for anything else.

        if (cardInfo.getCardNumber().equals("378282246310005")) {

            result = (UUID.randomUUID()).toString();

        } else if (cardInfo.getCardNumber().equals("2221001223630333")) {

            result = null;

        } else {

            throw new IllegalArgumentException();
        }

        return result;
    }
}
