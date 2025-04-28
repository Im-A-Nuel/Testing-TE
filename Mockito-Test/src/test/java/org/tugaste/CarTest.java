package org.tugaste;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

class CarTest {

    private final Car myCar = mock(Car.class);

    @Test
    public void def(){
        assertFalse(myCar.needsFuel());
    }

    @Test
    public void testIfCarIsCar() {
        assertInstanceOf(Car.class, myCar);
        System.out.println(Car.class);
        System.out.println(myCar);
    }

    @Test
    public void testDefaultValueOfMyCar() {
//        System.out.println(myCar.needsFuel());
//        assertTrue(myCar.needsFuel(), "my car need fuel");
        assertEquals(0.0, myCar.getEngineTemperature(), 1e-3, "my car temperature should be 0.0");
    }

    @Test
    public void testGiveValueOnMockObject() {
//        assertFalse(myCar.needsFuel(), "my car don't need fuel!");
        when(myCar.needsFuel()).thenReturn(false);
        assertFalse(myCar.needsFuel(), "my car need fuel!");
    }

    @Test
    public void stubbingTest(){
        when(myCar.needsFuel()).thenReturn(true);
        assertTrue(myCar.needsFuel());
    }


    @Test
    public void stubbingTestGetTemp(){
        when(myCar.getEngineTemperature())
                .thenReturn(80.0)
                .thenReturn(90.0);

        assertEquals(80.0, myCar.getEngineTemperature());
        assertEquals(90.0, myCar.getEngineTemperature());
    }

    @Test
    public void testDriveTo(){
        myCar.driveTo("Jakarta");

        verify(myCar).driveTo("Jakarta");
    }
    @Test
    public void testVerifyDriveTo(){
        myCar.needsFuel();
        myCar.needsFuel();
//        myCar.getEngineTemperature();

        verify(myCar, times(2)).needsFuel();  // Memastikan needsFuel() dipanggil 2 kali
        verify(myCar, never()).getEngineTemperature();  // Memastikan getEngineTemperature() tidak pernah dipanggil
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