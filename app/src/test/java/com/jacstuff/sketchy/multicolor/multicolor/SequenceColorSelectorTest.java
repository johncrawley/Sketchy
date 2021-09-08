package com.jacstuff.sketchy.multicolor.multicolor;

import com.jacstuff.sketchy.multicolor.SequenceColorSelector;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SequenceColorSelectorTest {

  //  MainViewModel mainViewModel = Providers.
    private SequenceColorSelector sequenceColorSelector;
    private List<Integer> fewColors;
    private List<Integer> manyColors;


    @Before
    public void setup(){
      sequenceColorSelector = new SequenceColorSelector(null, null);
      fewColors = setupColorsList(5);
      manyColors = setupColorsList(50);
    }


    public List<Integer> setupColorsList(int capacity){
      List<Integer> list = new ArrayList<>(capacity);
      for(int i= 0; i < capacity; i++){
        list.add(i);
      }
      return list;
    }


    @Test
    public void canCalculateCorrectMaxIndexOfSequence(){
      int result = sequenceColorSelector.getMaxIndexOfSequence(100, fewColors);
      assertEquals(fewColors.size()-1, result );

      // there should be at least 2 colors in each sequence, so max index should never be less than 1
      result = sequenceColorSelector.getMaxIndexOfSequence(0, fewColors);
      assertEquals( 1, result);

      // trying for the larger list
      result = sequenceColorSelector.getMaxIndexOfSequence(25, manyColors);
      int expectedIndex = manyColors.size() / 4;
      assertEquals( expectedIndex, result);
    }


    @Test
    public void canCalculateMinIndexOfSequence(){
      int result = sequenceColorSelector.getMinIndexOfSequence(100, fewColors);
      int expected = fewColors.size() -2;
      assertEquals(expected, result);

      result = sequenceColorSelector.getMinIndexOfSequence(0, manyColors);
      expected = 0;
      assertEquals(expected, result);



    }

}
