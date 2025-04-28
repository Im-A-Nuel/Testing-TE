package org.tugaste;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CarTest {

    private final Car myCar = mock(Car.class);

    @Test
    public void testIfCarIsCar() {
        assertInstanceOf(Car.class, myCar);
    }

    @Test
    public void testDefaultValueOfMyCar() {
        System.out.println(myCar.needsFuel());
        assertTrue(myCar.needsFuel(), "my car need fuel");
        assertEquals(0.0, myCar.getEngineTemperature(), 1e-3, "my car temperature should be 0.0");
    }

    @Test
    public void testGiveValueOnMockObject() {
        assertFalse(myCar.needsFuel(), "my car don't need fuel!");
        when(myCar.needsFuel()).thenReturn(true);
        assertFalse(myCar.needsFuel(), "my car need fuel!");
    }

    @Test
    public void testMyCarHaveException(){
        // mengkondisikan bila method needsfuel
        // dipanggil akan memberikan ekspresi

        when(myCar.needsFuel())
                .thenThrow(new RuntimeException());
        myCar.needsFuel();
    }

}