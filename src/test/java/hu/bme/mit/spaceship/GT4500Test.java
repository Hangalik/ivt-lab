package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class GT4500Test {

  private GT4500 ship;
  
  private TorpedoStore mockPrimaryTS;
  private TorpedoStore mockSecondaryTS;

  @Before
  public void init(){
    this.ship = new GT4500();
    mockPrimaryTS = mock(TorpedoStore.class);
    mockSecondaryTS = mock(TorpedoStore.class);
    ship.setPrimaryTorpedoStore(mockPrimaryTS);
    ship.setSecondaryTorpedoStore(mockSecondaryTS);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
	  when(mockPrimaryTS.isEmpty()).thenReturn(false);
	  when(mockPrimaryTS.fire(1)).thenReturn(true);
	  
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimaryTS, times(1)).isEmpty();
    verify(mockPrimaryTS, times(1)).fire(1);
    verify(mockSecondaryTS, times(0)).isEmpty();
    verify(mockSecondaryTS, times(0)).fire(1);
    assertEquals(true, result);
  }
  
  @Test
  public void fireTorpedo_Alternating_Success() {
	// Arrange
	  when(mockPrimaryTS.isEmpty()).thenReturn(false);
	  when(mockPrimaryTS.fire(1)).thenReturn(true);
	  when(mockSecondaryTS.isEmpty()).thenReturn(false);
	  when(mockSecondaryTS.fire(1)).thenReturn(true);
	  
	  //Act
	  boolean firstResult = ship.fireTorpedo(FiringMode.SINGLE);
	  
	  //Assert
	  verify(mockPrimaryTS, times(1)).isEmpty();
	  verify(mockPrimaryTS, times(1)).fire(1);
	  verify(mockSecondaryTS, times(0)).isEmpty();
	  verify(mockSecondaryTS, times(0)).fire(1);
	  assertEquals(true, firstResult);
	  
	  //Act
	  boolean secondResult = ship.fireTorpedo(FiringMode.SINGLE);
	  
	  //Assert
	  verify(mockPrimaryTS, times(1)).isEmpty();
	  verify(mockPrimaryTS, times(1)).fire(1);
	  verify(mockSecondaryTS, times(1)).isEmpty();
	  verify(mockSecondaryTS, times(1)).fire(1);
	  assertEquals(true, secondResult);
  }
  
  @Test
  public void fireTorpedo_PrimaryEmptyTS_Success() {
	  //Arrange
	  when(mockPrimaryTS.isEmpty()).thenReturn(true);
	  when(mockSecondaryTS.isEmpty()).thenReturn(false);
	  when(mockSecondaryTS.fire(1)).thenReturn(true);
	  
	  //Act
	  boolean result = ship.fireTorpedo(FiringMode.SINGLE);
	  
	  //Assert
	  verify(mockPrimaryTS, times(1)).isEmpty();
	  verify(mockPrimaryTS, times(0)).fire(1);
	  verify(mockSecondaryTS, times(1)).isEmpty();
	  verify(mockSecondaryTS, times(1)).fire(1);
	  assertEquals(true, result);
  }
  
  @Test
  public void fireTorpedo_SecondaryEmpty_Success() {
	//Arrange
	  when(mockPrimaryTS.isEmpty()).thenReturn(false);
	  when(mockPrimaryTS.fire(1)).thenReturn(true);
	  when(mockSecondaryTS.isEmpty()).thenReturn(true);
	  
	  //Act
	  boolean firstResult = ship.fireTorpedo(FiringMode.SINGLE);
	  boolean secondResult = ship.fireTorpedo(FiringMode.SINGLE);
	  
	  //Assert
	  verify(mockPrimaryTS, times(2)).isEmpty();
	  verify(mockPrimaryTS, times(2)).fire(1);
	  verify(mockSecondaryTS, times(1)).isEmpty();
	  verify(mockSecondaryTS, times(0)).fire(1);
	  assertEquals(true, secondResult);
	  assertEquals(true, firstResult);
  }
  
  @Test
  public void fireTorpedo_Single_Failure() {
	  //Arrange
	  when(mockPrimaryTS.isEmpty()).thenReturn(false);
	  when(mockPrimaryTS.fire(1)).thenReturn(false);
	  when(mockSecondaryTS.isEmpty()).thenReturn(false);
	  when(mockSecondaryTS.fire(1)).thenReturn(true);
	  
	  //Act
	  boolean result = ship.fireTorpedo(FiringMode.SINGLE);
	  
	  //Assert
	  verify(mockPrimaryTS, times(1)).isEmpty();
	  verify(mockPrimaryTS, times(1)).fire(1);
	  verify(mockSecondaryTS, times(0)).isEmpty();
	  verify(mockSecondaryTS, times(0)).fire(1);
	  assertEquals(false, result);
  }
  
  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
	  when(mockPrimaryTS.isEmpty()).thenReturn(false);
	  when(mockPrimaryTS.fire(1)).thenReturn(true);
	  when(mockSecondaryTS.isEmpty()).thenReturn(false);
	  when(mockSecondaryTS.fire(1)).thenReturn(true);
	  
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimaryTS, times(1)).isEmpty();
    verify(mockPrimaryTS, times(1)).fire(1);
    verify(mockSecondaryTS, times(1)).isEmpty();
    verify(mockSecondaryTS, times(1)).fire(1);
    assertEquals(true, result);
  }
  
  @Test
  public void fireTorpedo_All_Failure(){
	    // Arrange
		  when(mockPrimaryTS.isEmpty()).thenReturn(true);
		  when(mockPrimaryTS.fire(1)).thenReturn(true);
		  when(mockSecondaryTS.isEmpty()).thenReturn(false);
		  when(mockSecondaryTS.fire(1)).thenReturn(true);
		  
	    // Act
	    boolean result = ship.fireTorpedo(FiringMode.ALL);

	    // Assert
	    verify(mockPrimaryTS, times(1)).isEmpty();
	    verify(mockPrimaryTS, times(0)).fire(1);
	    verify(mockSecondaryTS, times(1)).isEmpty();
	    verify(mockSecondaryTS, times(1)).fire(1);
	    assertEquals(false, result);
	  }
  
  @Test
  public void fireLaser_Failure() {
	  //Arrange
	  
	  //Act
	  boolean result = ship.fireLaser(FiringMode.SINGLE);
	  
	  //Assert
	  assertEquals(false, result);
  }
  
  @Test
  public void fireTorpedo_All_Single_BothEmpty_Failure() {
	  //Arrange
	  when(mockPrimaryTS.isEmpty()).thenReturn(true);
	  when(mockSecondaryTS.isEmpty()).thenReturn(true);
	  
	  //Act
	  boolean firstRresult = ship.fireTorpedo(FiringMode.SINGLE);
	  boolean secondRresult = ship.fireTorpedo(FiringMode.ALL);
	  
	  //Assert
	  verify(mockPrimaryTS, times(2)).isEmpty();
	    verify(mockPrimaryTS, times(0)).fire(1);
	    verify(mockSecondaryTS, times(2)).isEmpty();
	    verify(mockSecondaryTS, times(0)).fire(1);
	    assertEquals(false, firstRresult);
	    assertEquals(false, secondRresult);
  }

}
