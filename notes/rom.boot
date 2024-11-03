
* = $FB4D
FB4D   A2 FF                LDX #$FF
FB4F   9A                   TXS
FB50   D8                   CLD
FB51   18                   CLC
FB52   A9 00                LDA #$00
FB54   85 14                STA $14
FB56   A9 00                LDA #$00
FB58   85 11                STA $11
FB5A   85 12                STA $12
FB5C   A9 C4                LDA #$C4
FB5E   85 15                STA $15
FB60   A9 34                LDA #$34
FB62   85 18                STA $18
FB64   A9 00                LDA #$00
FB66   85 1A                STA $1A
FB68   85 16                STA $16
FB6A   85 4A                STA $4A
FB6C   A9 6C                LDA #$6C
FB6E   85 4B                STA $4B
FB70   A9 EF                LDA #$EF
FB72   85 46                STA $46
FB74   A9 F5                LDA #$F5
FB76   85 47                STA $47
FB78   A9 AE                LDA #$AE
FB7A   85 44                STA $44
FB7C   A9 FF                LDA #$FF
FB7E   85 45                STA $45
FB80   A9 00                LDA #$00
FB82   85 48                STA $48
FB84   A9 03                LDA #$03
FB86   85 49                STA $49
FB88   A0 05                LDY #$05
FB8A   20 EC FB             JSR LFBEC
FB8D   AD 0E 03             LDA $030E
FB90   C9 5A                CMP #$5A
FB92   D0 07                BNE LFB9B
FB94   AD 0F 03             LDA $030F
FB97   C9 A5                CMP #$A5
FB99   F0 15                BEQ LFBB0
FB9B   A9 4A      LFB9B     LDA #$4A
FB9D   85 40                STA $40
FB9F   85 42                STA $42
FBA1   85 5B                STA $5B
FBA3   A9 FB                LDA #$FB
FBA5   85 41                STA $41
FBA7   85 43                STA $43
FBA9   85 5C                STA $5C
FBAB   A0 0D                LDY #$0D
FBAD   20 EC FB             JSR LFBEC
FBB0   A9 FB      LFBB0     LDA #$FB
FBB2   85 4F                STA $4F
FBB4   A9 F5                LDA #$F5
FBB6   85 4E                STA $4E
FBB8   58                   CLI
FBB9   A2 C2                LDX #$C2
FBBB   A9 04                LDA #$04
FBBD   85 5E                STA $5E
FBBF   A0 00                LDY #$00
FBC1   84 5D                STY $5D
FBC3   98         LFBC3     TYA
FBC4   B1 5D                LDA ($5D),Y
FBC6   C9 5A                CMP #$5A
FBC8   D0 14                BNE LFBDE
FBCA   C8                   INY
FBCB   B1 5D                LDA ($5D),Y
FBCD   C9 A5                CMP #$A5
FBCF   D0 0D                BNE LFBDE
FBD1   C8                   INY
FBD2   B1 5D                LDA ($5D),Y
FBD4   85 4E                STA $4E
FBD6   C8                   INY
FBD7   B1 5D                LDA ($5D),Y
FBD9   85 4F                STA $4F
FBDB   4C 28 F4   LFBDB     JMP $F428
FBDE   A4 5E      LFBDE     LDY $5E
FBE0   C8                   INY
FBE1   C8                   INY
FBE2   C8                   INY
FBE3   C8                   INY
FBE4   F0 F5                BEQ LFBDB
FBE6   84 5E                STY $5E
FBE8   A0 00                LDY #$00
FBEA   F0 D7                BEQ LFBC3
FBEC   B9 00 F4   LFBEC     LDA $F400,Y
FBEF   91 48                STA ($48),Y
FBF1   88                   DEY
FBF2   10 F8                BPL LFBEC
FBF4   60                   RTS
;----------------------------------------------------------------------
FBF5   13                   ???                ;%00010011
FBF6   F6 31                INC $31,X
FBF8   FA                   ???                ;%11111010
FBF9   06 4E                ASL $4E
FBFB   4F                   ???                ;%01001111 'O'
FBFC   20 52 4F             JSR $4F52
FBFF   4D B6 FF             EOR $FFB6
FC02   AF                   ???                ;%10101111
FC03   F8                   SED
FC04   93                   ???                ;%10010011
FC05   FF                   ???                ;%11111111
FC06   AF                   ???                ;%10101111
FC07   F8                   SED
FC08   11 FE                ORA ($FE),Y
FC0A   0C                   ???                ;%00001100
FC0B   FC                   ???                ;%11111100
FC0C   0E FC A9             ASL $A9FC
FC0F   DF                   ???                ;%11011111
;----------------------------------------------------------------------
FC10   85 51                STA $51
FC12   A9 FF                LDA #$FF
FC14   85 52                STA $52
FC16   A9 01                LDA #$01
FC18   85 53                STA $53
FC1A   A9 80                LDA #$80
FC1C   20 B5 FD             JSR $FDB5
FC1F   A0 80                LDY #$80
FC21   20 93 FD             JSR $FD93
FC24   D0 03                BNE $FC29
FC26   4C 5F 00             JMP $005F
.END

;auto-generated symbols and labels
LFBEC      $FBEC
LFB9B      $FB9B
LFBB0      $FBB0
LFBDE      $FBDE
LFBDB      $FBDB
LFBC3      $FBC3

