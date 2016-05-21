package edu.utexas.cs.nn.tasks.rlglue.tetris;

import static org.junit.Assert.*;

import java.util.List;
import java.util.HashSet;

import org.junit.Test;
import org.rlcommunity.environments.tetris.TetrisState;

import edu.utexas.cs.nn.util.MiscUtil;
import edu.utexas.cs.nn.util.datastructures.Pair;

public class TetrisAfterStateGeneratorTests {

	@Test
	public void test_afterstate_occurance() { // This checks if all afterstates are present in the returned hash and This part tests that the actions of the action list do give you the given state
		TetrisState testState = new TetrisState(); // makes a Tetris state to test with
		//simple example used
		testState.worldState[190] = 1;
		testState.worldState[191] = 1;
		testState.worldState[196] = 1;
		testState.worldState[197] = 1;
		testState.worldState[198] = 1;
		testState.worldState[199] = 1;
		testState.currentX = 3;
		testState.currentY = -2;
		testState.currentRotation = 0;
		System.out.println(testState);
		MiscUtil.waitForReadStringAndEnterKeyPress();
		HashSet<TetrisStateActionPair> holder = TetrisAfterStateGenerator.generateAfterStates(testState);
		HashSet<TetrisState> justStates = new HashSet<TetrisState>();
		
		//WOO BOY GET READY FOR A LOT OF TESTING CODE NONSENSE -Gab
		TetrisState resultV1 = new TetrisState(testState); //Vertical 1	
		resultV1.worldState[150] = 1;
		resultV1.worldState[160] = 1;
		resultV1.worldState[170] = 1;
		resultV1.worldState[180] = 1;
		resultV1.currentX -= 6;
		resultV1.currentY += 14;
		resultV1.currentRotation += 1;

		TetrisState resultV2 = new TetrisState(testState); //Vertical 2
		resultV2.worldState[151] = 1;
		resultV2.worldState[161] = 1;
		resultV2.worldState[171] = 1;
		resultV2.worldState[181] = 1;
		resultV2.currentX -= 5;
		resultV2.currentY += 14;
		resultV2.currentRotation += 1;

		TetrisState resultV3 = new TetrisState(testState); //Vertical 3
		resultV3.worldState[162] = 1;
		resultV3.worldState[172] = 1;
		resultV3.worldState[182] = 1;
		resultV3.worldState[192] = 1;
		resultV3.currentX -= 4;
		resultV3.currentY += 15;
		resultV3.currentRotation += 1;

		TetrisState resultV4 = new TetrisState(testState); //Vertical 4
		resultV4.worldState[163] = 1;
		resultV4.worldState[173] = 1;
		resultV4.worldState[183] = 1;
		resultV4.worldState[193] = 1;
		resultV4.currentX -= 3;
		resultV4.currentY += 15;
		resultV4.currentRotation += 1;

		TetrisState resultV5 = new TetrisState(testState); //Vertical 5
		resultV5.worldState[164] = 1;
		resultV5.worldState[174] = 1;
		resultV5.worldState[184] = 1;
		resultV5.worldState[194] = 1;
		resultV5.currentX -= 2;
		resultV5.currentY += 15;
		resultV5.currentRotation += 1;

		TetrisState resultV6 = new TetrisState(testState); //Vertical 6
		resultV6.worldState[165] = 1;
		resultV6.worldState[175] = 1;
		resultV6.worldState[185] = 1;
		resultV6.worldState[195] = 1;
		resultV6.currentX -= 1;
		resultV6.currentY += 15;
		resultV6.currentRotation += 1;

		TetrisState resultV7 = new TetrisState(testState); //Vertical 7
		resultV7.worldState[156] = 1;
		resultV7.worldState[166] = 1;
		resultV7.worldState[176] = 1;
		resultV7.worldState[186] = 1;
		resultV7.currentX -= 0;
		resultV7.currentY += 14;
		resultV7.currentRotation += 1;
		
		TetrisState resultV8 = new TetrisState(testState); //Vertical 8
		resultV8.worldState[157] = 1;
		resultV8.worldState[167] = 1;
		resultV8.worldState[177] = 1;
		resultV8.worldState[187] = 1;
		resultV8.currentX += 1;
		resultV8.currentY += 14;
		resultV8.currentRotation += 1;
		
		TetrisState resultV9 = new TetrisState(testState); //Vertical 9
		resultV9.worldState[158] = 1;
		resultV9.worldState[168] = 1;
		resultV9.worldState[178] = 1;
		resultV9.worldState[188] = 1;
		resultV9.currentX += 2;
		resultV9.currentY += 14;
		resultV9.currentRotation += 1;

		TetrisState resultV10 = new TetrisState(testState); //Vertical 10
		resultV10.worldState[159] = 1;
		resultV10.worldState[169] = 1;
		resultV10.worldState[179] = 1;
		resultV10.worldState[189] = 1;
		resultV10.currentX += 3;
		resultV10.currentY += 14;
		resultV10.currentRotation += 1;
		
		TetrisState resultH1 = new TetrisState(testState); //Horizontal 1
		resultH1.worldState[180] = 1;
		resultH1.worldState[181] = 1;
		resultH1.worldState[182] = 1;
		resultH1.worldState[183] = 1;
		resultH1.currentX -= 4;
		resultH1.currentY += 16;

		TetrisState resultH2 = new TetrisState(testState); //Horizontal 2
		resultH2.worldState[181] = 1;
		resultH2.worldState[182] = 1;
		resultH2.worldState[183] = 1;
		resultH2.worldState[184] = 1;
		resultH2.currentX -= 3;
		resultH2.currentY += 16;

		TetrisState resultH3 = new TetrisState(); //Horizontal 3
		resultH3.currentX -= 2;
		resultH3.currentY += 17;

		TetrisState resultH4 = new TetrisState(testState); //Horizontal 4
		resultH4.worldState[183] = 1;
		resultH4.worldState[184] = 1;
		resultH4.worldState[185] = 1;
		resultH4.worldState[186] = 1;
		resultH4.currentX -= 1;
		resultH4.currentY += 16;

		TetrisState resultH5 = new TetrisState(testState); //Horizontal 5
		resultH5.worldState[184] = 1;
		resultH5.worldState[185] = 1;
		resultH5.worldState[186] = 1;
		resultH5.worldState[187] = 1;
		resultH5.currentX -= 0;
		resultH5.currentY += 16;

		TetrisState resultH6 = new TetrisState(testState); //Horizontal 6
		resultH6.worldState[185] = 1;
		resultH6.worldState[186] = 1;
		resultH6.worldState[187] = 1;
		resultH6.worldState[188] = 1;
		resultH6.currentX += 1;
		resultH6.currentY += 16;
		
		TetrisState resultH7 = new TetrisState(testState); //Horizontal 7
		resultH7.worldState[186] = 1;
		resultH7.worldState[187] = 1;
		resultH7.worldState[188] = 1;
		resultH7.worldState[189] = 1;
		resultH7.currentX += 2;
		resultH7.currentY += 16;
				
		for(TetrisStateActionPair i : holder){ // transfers only the tetris states to the hash set
			justStates.add(i.t1);
		}

		assertTrue(holder.contains(new TetrisStateActionPair(resultV1, null)));
		assertTrue(holder.contains(new TetrisStateActionPair(resultV2, null)));
		assertTrue(holder.contains(new TetrisStateActionPair(resultV3, null)));
		assertTrue(holder.contains(new TetrisStateActionPair(resultV4, null)));
		assertTrue(holder.contains(new TetrisStateActionPair(resultV5, null)));
		assertTrue(holder.contains(new TetrisStateActionPair(resultV6, null)));
		assertTrue(holder.contains(new TetrisStateActionPair(resultV7, null)));
		assertTrue(holder.contains(new TetrisStateActionPair(resultV8, null)));
		assertTrue(holder.contains(new TetrisStateActionPair(resultV9, null)));
		assertTrue(holder.contains(new TetrisStateActionPair(resultV10, null)));
		assertTrue(holder.contains(new TetrisStateActionPair(resultH1, null)));
		assertTrue(holder.contains(new TetrisStateActionPair(resultH2, null)));
		assertTrue(holder.contains(new TetrisStateActionPair(resultH3, null)));
		assertTrue(holder.contains(new TetrisStateActionPair(resultH4, null)));
		assertTrue(holder.contains(new TetrisStateActionPair(resultH5, null)));
		assertTrue(holder.contains(new TetrisStateActionPair(resultH6, null)));
		assertTrue(holder.contains(new TetrisStateActionPair(resultH7, null)));
		
		
		//This part tests that the actions of the action list do give you the given state
		for(TetrisStateActionPair j : holder){
			TetrisState copyState = new TetrisState(testState); 
			List<Integer> actionList = j.t2;
			//actions
			for(int p = 0; p < actionList.size(); p++){
				copyState.take_action(actionList.get(p)); //move left
				copyState.update(); //update
			}
			assertTrue(j.t1.equals(copyState));
		}
		
	}
	
	public void starting_position_issue(){ // this test is for an issue we run into that won't allow the tri piece to make any initial moves (take_action)
		TetrisState testState = new TetrisState(); // makes a Tetris state to test with
		testState.currentBlockId = 2;
		testState.currentX = 3;
		testState.currentY = -1;
		testState.currentRotation = 0;
		System.out.println(testState);
		MiscUtil.waitForReadStringAndEnterKeyPress();
		HashSet<TetrisStateActionPair> holder = TetrisAfterStateGenerator.generateAfterStates(testState);
		HashSet<TetrisState> justStates = new HashSet<TetrisState>();
	}
	
	//Not really a formal test, but still useful -Gab
	@Test
	public void different_starting_blocks_positions(){ // This tests for the starting X and Y of each piece
		//TetrisViewer testView = new TetrisViewer(); //make a TetrisViewer
		//TetrisState testState = new TetrisState(); // makes a Tetris state to test with
		
		//these are testing each shape in the testing environment
		/*
		testView.update(testState);
		//System.out.println("For Piece " + testState.currentBlockId + ", x = " + testState.currentX + ", y = " + testState.currentY + ", and the rotation is " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		testState.currentBlockId = 1;
		testView.update(testState);
		//System.out.println("For Piece " + testState.currentBlockId + ", x = " + testState.currentX + ", y = " + testState.currentY + ", and the rotation is " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		testState.currentBlockId = 2;
		testView.update(testState);
		//System.out.println("For Piece " + testState.currentBlockId + ", x = " + testState.currentX + ", y = " + testState.currentY + ", and the rotation is " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		testState.currentBlockId = 3;
		testView.update(testState);
		//System.out.println("For Piece " + testState.currentBlockId + ", x = " + testState.currentX + ", y = " + testState.currentY + ", and the rotation is " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		testState.currentBlockId = 4;
		testView.update(testState);
		//System.out.println("For Piece " + testState.currentBlockId + ", x = " + testState.currentX + ", y = " + testState.currentY + ", and the rotation is " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		testState.currentBlockId = 5;
		testView.update(testState);
		//System.out.println("For Piece " + testState.currentBlockId + ", x = " + testState.currentX + ", y = " + testState.currentY + ", and the rotation is " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		testState.currentBlockId = 6;
		testView.update(testState);
		//System.out.println("For Piece " + testState.currentBlockId + ", x = " + testState.currentX + ", y = " + testState.currentY + ", and the rotation is " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		*/
		
		//These are for random spawns
		/*
		testState.spawn_block();
		testView.update(testState);
		//System.out.println("For Piece " + testState.currentBlockId + ", x = " + testState.currentX + ", y = " + testState.currentY + ", and the rotation is " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		testState.spawn_block();
		testView.update(testState);
		//System.out.println("For Piece " + testState.currentBlockId + ", x = " + testState.currentX + ", y = " + testState.currentY + ", and the rotation is " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		testState.spawn_block();
		testView.update(testState);
		//System.out.println("For Piece " + testState.currentBlockId + ", x = " + testState.currentX + ", y = " + testState.currentY + ", and the rotation is " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		testState.spawn_block();
		testView.update(testState);
		//System.out.println("For Piece " + testState.currentBlockId + ", x = " + testState.currentX + ", y = " + testState.currentY + ", and the rotation is " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		testState.spawn_block();
		testView.update(testState);
		//System.out.println("For Piece " + testState.currentBlockId + ", x = " + testState.currentX + ", y = " + testState.currentY + ", and the rotation is " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		testState.spawn_block();
		testView.update(testState);
		//System.out.println("For Piece " + testState.currentBlockId + ", x = " + testState.currentX + ", y = " + testState.currentY + ", and the rotation is " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		*/
		
		//this was to test the orientations
		/*
		testState.currentBlockId = 6;
		testView.update(testState);
		//System.out.println("x = " + testState.currentX + ", y = " + testState.currentY + ", r = " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		testState.currentRotation++;
		testView.update(testState);
		//System.out.println("x = " + testState.currentX + ", y = " + testState.currentY + ", r = " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		testState.currentRotation++;
		testView.update(testState);
		//System.out.println("x = " + testState.currentX + ", y = " + testState.currentY + ", r = " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		testState.currentRotation++;
		testView.update(testState);
		//System.out.println("x = " + testState.currentX + ", y = " + testState.currentY + ", r = " + testState.currentRotation);
		MiscUtil.waitForReadStringAndEnterKeyPress(); 
		*/
		
	}
	
	@Test
	public void afterstates_at_spawn() { // This tests that we get all available afterstates
		TetrisViewer testView = new TetrisViewer(); //make a TetrisViewer
		TetrisState testState = new TetrisState(); // makes a Tetris state to test with
		
		//this is testing for the amount of afterstates we get with just teh first block
		//we should get either 17 or 34, anything inbetween is ok(?) but anything below 17 poses an issue with our code
		testState.currentBlockId = 4; //simulating piece 4 at spawn point
		testState.currentX = 3;
		testState.currentY = -1;
		HashSet<TetrisStateActionPair> holder = TetrisAfterStateGenerator.generateAfterStates(testState);
		HashSet<TetrisState> justStates = new HashSet<TetrisState>();
		//System.out.println("holder size is " + holder.size());
		assertTrue(holder.size() >= 17 && holder.size() < 34);
		for(TetrisStateActionPair i : holder){ // transfers only the tetris states to the hash set
			justStates.add(i.t1);
		}
		//System.out.println("set size is " + justStates.size());
		assertTrue(justStates.size() == 17);
		
		
		//testView.update(testState);
		////System.out.println("x = " + testState.currentX + ", y = " + testState.currentY + ", r = " + testState.currentRotation);
		//MiscUtil.waitForReadStringAndEnterKeyPress(); 
		
	}
	
	@Test
	public void afterstates_after_action() { // This tests that we get all available afterstates when we've previous taken actions
		TetrisViewer testView = new TetrisViewer(); //make a TetrisViewer
		TetrisState testState = new TetrisState(); // makes a Tetris state to test with
		
		//This should test that we get 17 afterstates having taken other actions
		testState.currentBlockId = 4; //simulating piece 4 at spawn point
		testState.currentX = 3 + 2; // right by 2
		testState.currentY = -1 + 5; //down by 5
		testState.currentRotation = 3; // third rotation
		HashSet<TetrisStateActionPair> holder = TetrisAfterStateGenerator.generateAfterStates(testState);
		HashSet<TetrisState> justStates = new HashSet<TetrisState>();
		////System.out.println("holder size is " + holder.size());
		//assertTrue(holder.size() > 17 && holder.size() < 34);
		
		for(TetrisStateActionPair i : holder){ // transfers only the tetris states to the hash set
			justStates.add(i.t1);
		}
		assertTrue(justStates.size() == 17);
		System.out.println("state size should be " + justStates.size());
	
		testState.currentBlockId = 4; //simulating piece 4 at spawn point
		testState.currentX = 3 + 4; // right by 2
		testState.currentY = -1 + 8; //down by 5
		testState.currentRotation = 3; // third rotation
		HashSet<TetrisStateActionPair> holder2 = TetrisAfterStateGenerator.generateAfterStates(testState);
		HashSet<TetrisState> justStates2 = new HashSet<TetrisState>();
		assertTrue(holder.size() >= 17 && holder2.size() < 34);
		//System.out.println("holder size is " + holder2.size());
		for(TetrisStateActionPair i : holder2){ // transfers only the tetris states to the hash set
			justStates2.add(i.t1);
		}
		//System.out.println("state size should be " + justStates2.size());
		assertTrue(justStates2.size() == 17);
		
	}
	
	@Test
	public void afterstates_with_blocked_wall() { // This tests that we get all available afterstates when a wall if blocked off
		TetrisViewer testView = new TetrisViewer(); //make a TetrisViewer
		TetrisState testState = new TetrisState(); // makes a Tetris state to test with
		testState.currentBlockId = 4; //simulating piece 4 at spawn point
		testState.currentX = 3;
		testState.currentY = -1;
		testState.worldState[20] = 1;
		testState.worldState[30] = 1;
		testState.worldState[40] = 1;
		testState.worldState[50] = 1;
		testState.worldState[60] = 1;
		testState.worldState[70] = 1;
		testState.worldState[80] = 1;
		testState.worldState[90] = 1;
		testState.worldState[100] = 1;
		testState.worldState[110] = 1;
		testState.worldState[120] = 1;
		testState.worldState[130] = 1;
		testState.worldState[140] = 1;
		testState.worldState[150] = 1;
		testState.worldState[160] = 1;
		testState.worldState[170] = 1;
		testState.worldState[180] = 1;
		testState.worldState[190] = 1;
				
		HashSet<TetrisStateActionPair> holder = TetrisAfterStateGenerator.generateAfterStates(testState);
		HashSet<TetrisState> justStates = new HashSet<TetrisState>();
		for(TetrisStateActionPair i : holder){ // transfers only the tetris states to the hash set
			justStates.add(i.t1);
		}
		//System.out.println("set size is " + justStates.size());
		assertTrue(justStates.size() == 15);
	}

}