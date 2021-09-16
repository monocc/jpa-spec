package com.github.wenhao.lambda;

import com.github.wenhao.jpa.model.Address;
import com.github.wenhao.jpa.model.IdCard;
import com.github.wenhao.jpa.model.Person;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class CascadeFieldTest {

    @Test
    public void with() {
        final CascadeField<Person, Set<Address>> with = CascadeField.of(Person::getIdCard).with(IdCard::getPerson).with(Person::getAddresses);
        System.out.println(with.getCascades());
    }
}