package com.beta;

import org.junit.Assert;
import org.junit.Test;

public class BetaUserTableTest {
    @Test
    public void createFormatting() {
        Assert.assertEquals("email varchar(100), ", BetaUserTable.EMAIL.create());
    }

    @Test
    public void creationDoesntHaveExtraCommaSeparator() {
        String creation = BetaUserTable.creation();
        Assert.assertEquals("))", creation.substring(creation.length()-2));
    }
}