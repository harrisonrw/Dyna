/* Generated By:JJTree&JavaCC: Do not edit this line. TBLParserTokenManager.java */
package ca.uregina.engg.eil.dyna.interpreter;

public class TBLParserTokenManager implements TBLParserConstants
{
  public  java.io.PrintStream debugStream = System.out;
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0xfffe01e00000L) != 0L)
         {
            jjmatchedKind = 51;
            return 15;
         }
         if ((active0 & 0x2000L) != 0L)
            return 16;
         return -1;
      case 1:
         if ((active0 & 0x10000200000L) != 0L)
            return 15;
         if ((active0 & 0xfefe01c00000L) != 0L)
         {
            jjmatchedKind = 51;
            jjmatchedPos = 1;
            return 15;
         }
         return -1;
      case 2:
         if ((active0 & 0x401c00000L) != 0L)
            return 15;
         if ((active0 & 0xfefa00000000L) != 0L)
         {
            jjmatchedKind = 51;
            jjmatchedPos = 2;
            return 15;
         }
         return -1;
      case 3:
         if ((active0 & 0x720000000000L) != 0L)
            return 15;
         if ((active0 & 0x8cfa00000000L) != 0L)
         {
            jjmatchedKind = 51;
            jjmatchedPos = 3;
            return 15;
         }
         return -1;
      case 4:
         if ((active0 & 0x80ca00000000L) != 0L)
            return 15;
         if ((active0 & 0xc3000000000L) != 0L)
         {
            jjmatchedKind = 51;
            jjmatchedPos = 4;
            return 15;
         }
         return -1;
      case 5:
         if ((active0 & 0xc2000000000L) != 0L)
            return 15;
         if ((active0 & 0x1000000000L) != 0L)
         {
            jjmatchedKind = 51;
            jjmatchedPos = 5;
            return 15;
         }
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private final int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private final int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
private final int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 33:
         return jjMoveStringLiteralDfa1_0(0x10000L);
      case 35:
         return jjStopAtPos(0, 5);
      case 37:
         return jjStopAtPos(0, 11);
      case 40:
         return jjStopAtPos(0, 25);
      case 41:
         return jjStopAtPos(0, 26);
      case 42:
         jjmatchedKind = 9;
         return jjMoveStringLiteralDfa1_0(0x100L);
      case 43:
         return jjStopAtPos(0, 12);
      case 44:
         return jjStopAtPos(0, 32);
      case 45:
         return jjStartNfaWithStates_0(0, 13, 16);
      case 46:
         return jjStopAtPos(0, 27);
      case 47:
         return jjStopAtPos(0, 10);
      case 60:
         jjmatchedKind = 17;
         return jjMoveStringLiteralDfa1_0(0x80000L);
      case 61:
         jjmatchedKind = 14;
         return jjMoveStringLiteralDfa1_0(0x8000L);
      case 62:
         jjmatchedKind = 18;
         return jjMoveStringLiteralDfa1_0(0x100000L);
      case 91:
         return jjStopAtPos(0, 30);
      case 93:
         return jjStopAtPos(0, 31);
      case 97:
         return jjMoveStringLiteralDfa1_0(0x40000400000L);
      case 98:
         return jjMoveStringLiteralDfa1_0(0x1000000000L);
      case 99:
         return jjMoveStringLiteralDfa1_0(0x4000000000L);
      case 101:
         return jjMoveStringLiteralDfa1_0(0x20000000000L);
      case 102:
         return jjMoveStringLiteralDfa1_0(0x800800000000L);
      case 105:
         return jjMoveStringLiteralDfa1_0(0x10400000000L);
      case 110:
         return jjMoveStringLiteralDfa1_0(0x800000L);
      case 111:
         return jjMoveStringLiteralDfa1_0(0x200000L);
      case 112:
         return jjMoveStringLiteralDfa1_0(0x200000000L);
      case 114:
         return jjMoveStringLiteralDfa1_0(0x80000000000L);
      case 115:
         return jjMoveStringLiteralDfa1_0(0x202000000000L);
      case 116:
         return jjMoveStringLiteralDfa1_0(0x500000000000L);
      case 119:
         return jjMoveStringLiteralDfa1_0(0x8000000000L);
      case 120:
         return jjMoveStringLiteralDfa1_0(0x1000000L);
      case 123:
         return jjStopAtPos(0, 28);
      case 125:
         return jjStopAtPos(0, 29);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
private final int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 42:
         if ((active0 & 0x100L) != 0L)
            return jjStopAtPos(1, 8);
         break;
      case 61:
         if ((active0 & 0x8000L) != 0L)
            return jjStopAtPos(1, 15);
         else if ((active0 & 0x10000L) != 0L)
            return jjStopAtPos(1, 16);
         else if ((active0 & 0x80000L) != 0L)
            return jjStopAtPos(1, 19);
         else if ((active0 & 0x100000L) != 0L)
            return jjStopAtPos(1, 20);
         break;
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x900000000000L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x80000000000L);
      case 102:
         if ((active0 & 0x10000000000L) != 0L)
            return jjStartNfaWithStates_0(1, 40, 15);
         break;
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x8000000000L);
      case 108:
         return jjMoveStringLiteralDfa2_0(active0, 0x24800000000L);
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x400400000L);
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x1001800000L);
      case 113:
         return jjMoveStringLiteralDfa2_0(active0, 0x200000000000L);
      case 114:
         if ((active0 & 0x200000L) != 0L)
            return jjStartNfaWithStates_0(1, 21, 15);
         return jjMoveStringLiteralDfa2_0(active0, 0x400200000000L);
      case 115:
         return jjMoveStringLiteralDfa2_0(active0, 0x40000000000L);
      case 116:
         return jjMoveStringLiteralDfa2_0(active0, 0x2000000000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private final int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa3_0(active0, 0x4000000000L);
      case 100:
         if ((active0 & 0x400000L) != 0L)
            return jjStartNfaWithStates_0(2, 22, 15);
         break;
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x8200000000L);
      case 108:
         return jjMoveStringLiteralDfa3_0(active0, 0x800000000000L);
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x1800000000L);
      case 114:
         if ((active0 & 0x1000000L) != 0L)
            return jjStartNfaWithStates_0(2, 24, 15);
         return jjMoveStringLiteralDfa3_0(active0, 0x202000000000L);
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0x160000000000L);
      case 116:
         if ((active0 & 0x800000L) != 0L)
            return jjStartNfaWithStates_0(2, 23, 15);
         else if ((active0 & 0x400000000L) != 0L)
            return jjStartNfaWithStates_0(2, 34, 15);
         return jjMoveStringLiteralDfa3_0(active0, 0x80000000000L);
      case 117:
         return jjMoveStringLiteralDfa3_0(active0, 0x400000000000L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
private final int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0x800000000L);
      case 101:
         if ((active0 & 0x20000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 41, 15);
         else if ((active0 & 0x400000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 46, 15);
         return jjMoveStringLiteralDfa4_0(active0, 0x40000000000L);
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x2000000000L);
      case 107:
         if ((active0 & 0x100000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 44, 15);
         break;
      case 108:
         return jjMoveStringLiteralDfa4_0(active0, 0x9000000000L);
      case 110:
         return jjMoveStringLiteralDfa4_0(active0, 0x200000000L);
      case 115:
         return jjMoveStringLiteralDfa4_0(active0, 0x804000000000L);
      case 116:
         if ((active0 & 0x200000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 45, 15);
         break;
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x80000000000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
private final int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x8000000000L) != 0L)
            return jjStartNfaWithStates_0(4, 39, 15);
         else if ((active0 & 0x800000000000L) != 0L)
            return jjStartNfaWithStates_0(4, 47, 15);
         return jjMoveStringLiteralDfa5_0(active0, 0x1000000000L);
      case 110:
         return jjMoveStringLiteralDfa5_0(active0, 0x2000000000L);
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0xc0000000000L);
      case 115:
         if ((active0 & 0x4000000000L) != 0L)
            return jjStartNfaWithStates_0(4, 38, 15);
         break;
      case 116:
         if ((active0 & 0x200000000L) != 0L)
            return jjStartNfaWithStates_0(4, 33, 15);
         else if ((active0 & 0x800000000L) != 0L)
            return jjStartNfaWithStates_0(4, 35, 15);
         break;
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
private final int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa6_0(active0, 0x1000000000L);
      case 103:
         if ((active0 & 0x2000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 37, 15);
         break;
      case 110:
         if ((active0 & 0x80000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 43, 15);
         break;
      case 116:
         if ((active0 & 0x40000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 42, 15);
         break;
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
private final int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0); 
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 110:
         if ((active0 & 0x1000000000L) != 0L)
            return jjStartNfaWithStates_0(6, 36, 15);
         break;
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
private final void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private final void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private final void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}
private final void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}
private final void jjCheckNAddStates(int start)
{
   jjCheckNAdd(jjnextStates[start]);
   jjCheckNAdd(jjnextStates[start + 1]);
}
private final int jjMoveNfa_0(int startState, int curPos)
{
   int[] nextStates;
   int startsAt = 0;
   jjnewStateCnt = 15;
   int i = 1;
   jjstateSet[0] = startState;
   int j, kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 48)
                        kind = 48;
                     jjCheckNAddStates(0, 2);
                  }
                  else if (curChar == 45)
                     jjCheckNAddStates(3, 6);
                  else if (curChar == 34)
                     jjCheckNAddStates(7, 9);
                  break;
               case 15:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 51)
                        kind = 51;
                     jjCheckNAddTwoStates(5, 6);
                  }
                  else if (curChar == 58)
                  {
                     if (kind > 51)
                        kind = 51;
                     jjCheckNAddTwoStates(5, 6);
                  }
                  break;
               case 16:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(11, 12);
                  else if (curChar == 45)
                     jjCheckNAddTwoStates(10, 11);
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 48)
                        kind = 48;
                     jjCheckNAdd(9);
                  }
                  else if (curChar == 45)
                     jjCheckNAddTwoStates(8, 9);
                  break;
               case 1:
                  if (curChar == 32)
                     jjCheckNAddStates(7, 9);
                  break;
               case 2:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddStates(7, 9);
                  break;
               case 3:
                  if (curChar == 34 && kind > 50)
                     kind = 50;
                  break;
               case 5:
                  if (curChar != 58)
                     break;
                  kind = 51;
                  jjCheckNAddTwoStates(5, 6);
                  break;
               case 6:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 51)
                     kind = 51;
                  jjCheckNAddTwoStates(5, 6);
                  break;
               case 7:
                  if (curChar == 45)
                     jjCheckNAddStates(3, 6);
                  break;
               case 8:
                  if (curChar == 45)
                     jjCheckNAddTwoStates(8, 9);
                  break;
               case 9:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 48)
                     kind = 48;
                  jjCheckNAdd(9);
                  break;
               case 10:
                  if (curChar == 45)
                     jjCheckNAddTwoStates(10, 11);
                  break;
               case 11:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(11, 12);
                  break;
               case 12:
                  if (curChar == 46)
                     jjCheckNAdd(13);
                  break;
               case 13:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 49)
                     kind = 49;
                  jjCheckNAdd(13);
                  break;
               case 14:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 48)
                     kind = 48;
                  jjCheckNAddStates(0, 2);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 51)
                     kind = 51;
                  jjCheckNAddTwoStates(5, 6);
                  break;
               case 15:
               case 5:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 51)
                     kind = 51;
                  jjCheckNAddTwoStates(5, 6);
                  break;
               case 1:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                     jjAddStates(7, 9);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         MatchLoop: do
         {
            switch(jjstateSet[--i])
            {
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 15 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private final int jjMoveStringLiteralDfa0_1()
{
   switch(curChar)
   {
      case 10:
         return jjStopAtPos(0, 6);
      default :
         return 1;
   }
}
static final int[] jjnextStates = {
   9, 11, 12, 8, 9, 10, 11, 1, 2, 3, 
};
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, "\52\52", "\52", "\57", "\45", 
"\53", "\55", "\75", "\75\75", "\41\75", "\74", "\76", "\74\75", "\76\75", 
"\157\162", "\141\156\144", "\156\157\164", "\170\157\162", "\50", "\51", "\56", "\173", 
"\175", "\133", "\135", "\54", "\160\162\151\156\164", "\151\156\164", 
"\146\154\157\141\164", "\142\157\157\154\145\141\156", "\163\164\162\151\156\147", 
"\143\154\141\163\163", "\167\150\151\154\145", "\151\146", "\145\154\163\145", 
"\141\163\163\145\162\164", "\162\145\164\165\162\156", "\164\141\163\153", "\163\161\162\164", 
"\164\162\165\145", "\146\141\154\163\145", null, null, null, null, null, null, };
public static final String[] lexStateNames = {
   "DEFAULT", 
   "WithinComment1", 
};
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, 1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0xfffffffffff01L, 
};
static final long[] jjtoSkip = {
   0x7eL, 
};
static final long[] jjtoSpecial = {
   0x60L, 
};
static final long[] jjtoMore = {
   0x80L, 
};
protected SimpleCharStream input_stream;
private final int[] jjrounds = new int[15];
private final int[] jjstateSet = new int[30];
StringBuffer image;
int jjimageLen;
int lengthOfMatch;
protected char curChar;
public TBLParserTokenManager(SimpleCharStream stream){
   if (SimpleCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}
public TBLParserTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}
public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private final void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 15; i-- > 0;)
      jjrounds[i] = 0x80000000;
}
public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}
public void SwitchTo(int lexState)
{
   if (lexState >= 2 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   Token t = Token.newToken(jjmatchedKind);
   t.kind = jjmatchedKind;
   String im = jjstrLiteralImages[jjmatchedKind];
   t.image = (im == null) ? input_stream.GetImage() : im;
   t.beginLine = input_stream.getBeginLine();
   t.beginColumn = input_stream.getBeginColumn();
   t.endLine = input_stream.getEndLine();
   t.endColumn = input_stream.getEndColumn();
   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

public Token getNextToken() 
{
  int kind;
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {   
   try   
   {     
      curChar = input_stream.BeginToken();
   }     
   catch(java.io.IOException e)
   {        
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      matchedToken.specialToken = specialToken;
      return matchedToken;
   }
   image = null;
   jjimageLen = 0;

   for (;;)
   {
     switch(curLexState)
     {
       case 0:
         try { input_stream.backup(0);
            while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
               curChar = input_stream.BeginToken();
         }
         catch (java.io.IOException e1) { continue EOFLoop; }
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_0();
         break;
       case 1:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_1();
         if (jjmatchedPos == 0 && jjmatchedKind > 7)
         {
            jjmatchedKind = 7;
         }
         break;
     }
     if (jjmatchedKind != 0x7fffffff)
     {
        if (jjmatchedPos + 1 < curPos)
           input_stream.backup(curPos - jjmatchedPos - 1);
        if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           matchedToken = jjFillToken();
           matchedToken.specialToken = specialToken;
       if (jjnewLexState[jjmatchedKind] != -1)
         curLexState = jjnewLexState[jjmatchedKind];
           return matchedToken;
        }
        else if ((jjtoSkip[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           if ((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
           {
              matchedToken = jjFillToken();
              if (specialToken == null)
                 specialToken = matchedToken;
              else
              {
                 matchedToken.specialToken = specialToken;
                 specialToken = (specialToken.next = matchedToken);
              }
              SkipLexicalActions(matchedToken);
           }
           else 
              SkipLexicalActions(null);
         if (jjnewLexState[jjmatchedKind] != -1)
           curLexState = jjnewLexState[jjmatchedKind];
           continue EOFLoop;
        }
        jjimageLen += jjmatchedPos + 1;
      if (jjnewLexState[jjmatchedKind] != -1)
        curLexState = jjnewLexState[jjmatchedKind];
        curPos = 0;
        jjmatchedKind = 0x7fffffff;
        try {
           curChar = input_stream.readChar();
           continue;
        }
        catch (java.io.IOException e1) { }
     }
     int error_line = input_stream.getEndLine();
     int error_column = input_stream.getEndColumn();
     String error_after = null;
     boolean EOFSeen = false;
     try { input_stream.readChar(); input_stream.backup(1); }
     catch (java.io.IOException e1) {
        EOFSeen = true;
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
        if (curChar == '\n' || curChar == '\r') {
           error_line++;
           error_column = 0;
        }
        else
           error_column++;
     }
     if (!EOFSeen) {
        input_stream.backup(1);
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
     }
     throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
   }
  }
}

void SkipLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
}
