# Notes about RF65F*

RSC-Forth User's Manual
Rockwell International
Document No: 29651N51
Order No: 2148
Octuber 1983

## Memory

```
$0000   STACKS, SYSTEM VARIABLES, PORTS
$0100   OPTIONAL MODULE
$0200   --NOT USED--
$0300   USER VARIABLES, PAD, TIB ($20)
$0400   DEVICES ROM, RAM, 
$2000   DEVELOPMENT  ROM
$4000   --NOT USED--
$F400   KERNEL ROM
$FFFA   INTERRUPT TABLE

MARK = $A55A

SECTOR = $005F  ; reserved 128 bytes

```

### variables

```
CLD/WRM:    .word 0
UC/L:       .word 0
UR/W:       .word 0
UPAD:       .word 0
BASE:       .word 0
RO:         .word 0
SO:         .word 0
TIB:        .word 0 ;   reserved 32 bytes


KERNEL_ROM:
* =     $F400

* =     $FFFA
NMI:    $FF81
RST:    $FB4D
IRQ:    $FF84

## Forth stacks

048     UP  address of user area
04A     INTFLG
04B     (W-1) JMP opcode
04C     W   address of code field just interpreted by NEXT
04E     IP  address of instruction pointer
050     (N-1) (one byte before N to align the jmp at W-1)
051     N   utility area of 8 bytes,
059     XSAVE
05B     (COLD)
05D-0C1 data stack TOS      50 cells
0C2-0FF return stack TOS    30 cells

    SETUP moves a number in A (1, 2, 3, 4) cells from data stack to N area

    INTFLG  request bit 7 and inhibit bit 6, from interrupts

    ARM turns off inhibit 6

```

## Boot sequence

    0.  check boot: (: BOOT $030E @ $A55A = IF WARM ELSE COOL THEN ; )

    1.  search pages $0400 thru $3F00, for MARK then execute TURN-ON 
        (   $0400 BEGIN 
            DUP @ $A55A = IF
            CELL + EXEC EXIT THEN 
            $0100 + DUP $3F00 UNTIL )

    2.  if a block device, read 128 bytes at track 0 sector 1, 
        into $005F to $00DF, then jmp $005F

    3.  loop into serial line waiting for ^R ($12) then 
        wait one command by line:
        if Nxxxx is a hexadecimal number to push into stack
        else Wxxxx is a reference for a word to execute

## KERNEL ROM

table of rom words


 | MEMS  | WORD |
 | :----:  | :----: | 
 | F056 | DREAD | 
 | F40E | LIT | 
 | F458 | CLIT | 
 | F471 | EXECUTE | 
 | F480 | BRANCH | 
 | F497 | 0BRANCH | 
 | F4AF | (LOOP) | 
 | F4D0 | (+LOOP) | 
 | F4F8 | (DO) | 
 | F50D | DIGIT | 
 | F535 | (FIND) | 
 | F591 | ENCLOSE | 
 | F5D4 | EMIT | 
 | F5F6 | KEY | 
 | F60A | ?TERMINAL | 
 | F613 | CR | 
 | F626 | CMOVE | 
 | F646 | U* | 
 | F67B | U/ | 
 | F6BE | AND | 
 | F6CE | OR | 
 | F6DC | XOR | 
 | F6EA | SP@ | 
 | F6F3 | SP! |
 | F6FD | RP! |
 | F70C | RP@ |
 | F717 | ;S | 
 | F722 | LEAVE | 
 | F734 | >R | 
 | F73F | R | 
 | F73F | R> | 
 | F76B | 0< | 
 | F778 | + | 
 | F787 | D+ | 
 | F7A5 | 0= | 
 | F7A5 | NEGATE | 
 | F7B5 | DNEGATE | 
 | F7C5 | OVER | 
 | F7CF | DROP | 
 | F7D1 | 2DROP | 
 | F7D3 | SWAP | 
 | F7E5 | DUP | 
 | F7EF | 2DUP | 
 | F803 | BOUNDS | 
 | F80C | RP@ | 
 | F818 | +! | 
 | F820 | 2+ | 
 | F830 | TOGGLE | 
 | F83B | @ | 
 | F84B | C@ | 
 | F858 | ! | 
 | F868 | C! | 
 | F8AF | 0 | 
 | F8B3 | 1 | 
 | F8B7 | 2 | 
 | F8BB | 3 | 
 | F8BF | 4 | 
 | F8C3 | BL | 
 | F8C7 | TIB | 
 | F8CA | S0 | 
 | F8CD | R0 | 
 | F8D0 | UC/L | 
 | F8D3 | UPAD | 
 | F8D6 | UR/W | 
 | F8D9 | BASE | 
 | F8DC | CLD/WRM | 
 | F8DF | IN | 
 | F8E2 | DPL | 
 | F8E5 | HLD | 
 | F8E8 | C/L | 
 | F8F0 | PAD | 
 | F8F8 | 1+ | 
 | F913 | 1- | 
 | F920 | 2- | 
 | F938 | = | 
 | F93F | TYPE | 
 | F940 | U< | 
 | F954 | < | 
 | F96C | - | 
 | F96C | > | 
 | F974 | ROT | 
 | F98F | PICK | 
 | F99D | SPACE | 
 | F9A5 | -DUP | 
 | F9B1 | HEX | 
 | F9BC | DECIMAL | 
 | F9E3 | COUNT | 
 | FA0B | -TRAILING | 
 | FA31 | (.") | 
 | FA45 | EXPECT | 
 | FAAC | QUERY | 
 | FABE | FILL | 
 | FAE4 | ERASE | 
 | FAEC | BLANKS | 
 | FAF4 | HOLD | 
 | FB06 | <NUMBER) | 
 | FB48 | COLD | 
 | FC2C | S->D | 
 | FC36 | +- | 
 | FC42 | D+- | 
 | FC4E | ABS | 
 | FC56 | DABS | 
 | FC5E | MIN | 
 | FC6E | MAX | 
 | FC7E | M* | 
 | FC94 | M/ | 
 | FCB6 | * | 
 | FCBE | /MOD | 
 | FCCA | / | 
 | FCD4 | MOD | 
 | FCDC | */MOD | 
 | FCE8 | */ | 
 | FCF2 | M/MOD | 
 | FD08 | DISK | 
 | FD43 | SELECT | 
 | FDBE | DWRITE | 
 | FDF1 | INIT | 
 | FE11 | SEEK | 
 | FE3A | SPACES | 
 | FE52 | <# | 
 | FE5C | #> | 
 | FE6C | SIGN | 
 | FE7D | # | 
 | FEA0 | #S | 
 | FEB0 | D.R | 
 | FECE | D. | 
 | FED8 | .R | 
 | FEE4 | . | 
 | FEEC | ? | 
 | FEF4 | EEC! | 
 | FF42 | BANKC! | 
 | FF4A | BANKC@ | 
 | FF52 | BANKEEC! | 
 | FF5A | BANKEXECUTE | 
 |  |  | 

    Note:
    1. no address for inner interpreter or monitor
    2. address for DREAD $F056 maybe incorrect

## FORTH ROM

Table of external ROM

| MEMS  | WORD |
| :----:  | :----: | 
|  40B | TASK |
| 2032 | LIT |
| 203B | CLIT |
| 2047 | EXECUTE |
| 2052 | BRANCH |
| 205E | 0BRANCH |
| 2069 | (LOOP) |
| 2075 | (+LOOP) |
| 207E | (DO) |
| 2084 | I |
| 2090 | DIGIT |
| 209B | (FIND) |
| 20A7 | ENCLOSE |
| 20B0 | EMIT |
| 20B8 | KEY |
| 20C6 | ?TERMINAL |
| 20CD | CR |
| 20D5 | XON |
| 20E6 | XOFF |
| 20F9 | SOURCE |
| 2145 | FINIS |
| 215C | CMOVE |
| 2163 | U* |
| 216A | U/ |
| 2172 | AND |
| 2179 | OR |
| 2181 | XOR |
| 2189 | SP@ |
| 2191 | SP! |
| 2199 | RP! |
| 21A1 | RP@ |
| 21A8 | ;S |
| 21B2 | LEAVE |
| 21B9 | >R |
| 21C0 | R> |
| 21C6 | R |
| 21CD | 0= |
| 21D5 | NOT |
| 21DE | 0< |
| 21E4 | + |
| 21EB | D+ |
| 21F6 | NEGATE |
| 2202 | DNEGATE |
| 220B | OVER |
| 2214 | DROP |
| 221E | 2DROP |
| 2227 | SWAP |
| 222F | DUP |
| 2238 | 2DUP |
| 2243 | BOUNDS |
| 224A | +! |
| 2255 | TOGGLE |
| 225B | @ |
| 2262 | C@ |
| 2268 | ! |
| 226F | C! |
| 2285 | : |
| 22A3 | ; |
| 22BE | CONSTANT |
| 22D9 | VARIABLE |
| 22EC | CODE |
| 2303 | USER |
| 2317 | 0 |
| 231D | 1 |
| 2323 | 2 |
| 2329 | 3 |
| 232F | 4 |
| 2336 | BL |
| 233E | TIB |
| 2345 | S0 |
| 234C | R0 |
| 2355 | UC/L |
| 235E | UPAD |
| 2367 | UR/W |
| 2370 | BASE |
| 237C | CLD/WRM |
| 2383 | IN |
| 238B | DPL |
| 2393 | HLD |
| 239E | DISKNO |
| 23AE | CYLINDER |
| 23BC | B/SIDE |
| 23CA | UFIRST |
| 23D8 | ULIMIT |
| 23E6 | OFFSET |
| 23F3 | WIDTH |
| 2402 | WARNING |
| 240F | FENCE |
| 2419 | DP |
| 242B | HEADERLESS |
| 243B | VOC-LINK |
| 244F | UABORT |
| 245A | USE |
| 2466 | PREV |
| 2471 | BLK |
| 247C | SCR |
| 248B | CONTEXT |
| 249A | CURRENT |
| 24A7 | STATE |
| 24B2 | CSP |
| 24BE | MODE |
| 24C9 | KHZ |
| 24D4 | C/L |
| 24DE | FIRST |
| 24F0 | LIMIT |
| 2500 | PAD |
| 2507 | 1+ |
| 250E | 2+ |
| 2515 | 1- |
| 251C | 2- |
| 2524 | DP/ |
| 253E | HERE/ |
| 2551 | ALLOT/ |
| 2560 | ,/ |
| 2575 | HERE |
| 2587 | ALLOT |
| 2595 | , |
| 25A8 | C, |
| 25BA | - |
| 25C0 | = |
| 25C7 | U< |
| 25CD | < |
| 25D3 | > |
| 25DB | ROT |
| 25E4 | PICK |
| 25EE | SPACE |
| 25F7 | -DUP |
| 2604 | TRAVERSE |
| 262A | LATEST |
| 263C | LFA |
| 2646 | CFA |
| 2656 | NFA | 
| 2670 | PFAPTR | 
| 2686 | !CSP | 
| 269B | ?ERROR | 
| 26B3 | ?COMP |
| 26CC | ?EXEC |
| 26E4 | ?PAIRS |
| 26F8 | ?CSP |
| 2715 | COMPILE |
| 272D | [ |
| 273D | ] |
| 2753 | SMUDGE |
| 2765 | HEX |
| 2771 | DECIMAL |
| 277D | (;CODE) |
| 2795 | ;CODE |
| 27AF | <BUILDS |
| 27C1 | DOES> |
| 27DD | COUNT |
| 27E6 | TYPE |
| 27F4 | -TRAILING |
| 27FD | (.") |
| 2804 | ." |
| 2836 | EXPECT |
| 2840 | QUERY | 
| 2846 | ???? | 
| 286D | FILL |
| 2877 | ERASE |
| 2882 | BLANKS |
| 288B | HOLD |
| 2894 | WORD |
| 28E6 | (NUMBER) |
| 28F1 | NUMBER |
| 2949 | -FIND |
| 2977 | (ABORT) |
| 298B | ERROR |
| 29C8 | ID. |
| 29F1 | CREATE |
| 2A94 | [COMPILE] |
| 2AB2 | LITERAL |
| 2AE5 | DLITERAL |
| 2B02 | ?STACK |
| 2B2D | INTERPRET |
| 2B7D | IMMEDIATE |
| 2B97 | VOCABULARY |
| 2BCS | FORTH |
| 2BE1 | ASSEMBLER |
| 2BF9 | DEFINITIONS |
| 2C0B | ( |
| 2C1D | QUIT |
| 2C4C | ABORT |
| 2CB9 | COLD |
| 2CC2 | S->D |
| 2CC9 | +- |
| 2CD1 | D+- |
| 2CD9 | ABS |
| 2CE2 | DABS |
| 2CEA | MIN |
| 2CF2 | MAX |
| 2CF9 | M* |
| 2D00 | M/ |
| 2D06 | * |
| 2D0F | /MOD |
| 2D15 | / |
| 2D1D | MOD |
| 2D27 | */MOD |
| 2D2E | */ |
| 2D38 | M/MOD |
| 2D41 | +BUF |
| 2D72 | UPDATE |
| 2D9A | EMPTY-BUFFERS |
| 2DBF | BUFFER |
| 2E09 | BLOCK |
| 2E69 | FLUSH |
| 2E94 | DUMP |
| 2ED7 | (LINE) |
| 2EFB | .LINE |
| 2F0F | >LINE |
| 2F40 | MESSAGE |
| 2F99 | LOAD |
| 2FC9 | —-> |
| 2FED | -BCD |
| 3009 | B/BUF |
| 3017 | B/SCR |
| 3023 | R/W |
| 3036 | DISK |
| 3041 | SELECT |
| 304B | DREAD |
| 3056 | DWRITE |
| 305F | INIT |
| 3068 | SEEK |
| 306E | ‘ |
| 3086 | H/C |
| 30BC | HWORD |
| 3110 | ?KERNEL |
| 3149 | AUTOSTART |
| 3185 | FORGET |
| 3226 | BEGIN |
| 323A | ENDIF |
| 3257 | THEN |
| 3264 | DO |
| 3279 | LOOP |
| 3291 | +LOOP |
| 32A9 | UNTIL |
| 32BF | END |
| 32CF | AGAIN |
| 32E8 | REPEAT |
| 3301 | IF |
| 331A | ELSE |
| 333E | WHILE |
| 3351 | SPACES |
| 3358 | <# |
| 335F | #> |
| 3368 | SIGN |
| 336E | 1 |
| 3375 | #S |
| 337D | D.R |
| 3384 | D. |
| 338B | .R |
| 3391 | . |
| 3397 | ? |
| 33A0 | LIST |
| 33EC | INDEX |
| 345B | VLIST |
| 349F | MON |
| 34AC | .S |
| 34EF | C,CON |
| 3518 | INTFLG |
| 3528 | INTVEC |
| 3538 | IRQVEC |
| 3548 | NMIVEC |
| 3554 | PA |
| 3560 | PB |
| 356C | PC |
| 3578 | PD |
| 3584 | PE |
| 3590 | PF |
| 359C | PG |
| 35A9 | IFR |
| 35B6 | IER |
| 35C3 | MCR |
| 35D1 | SCCR |
| 35DF | SCSR |
| 35ED | SCDR |
| 35FD | MEMTOP |
| 361E | CASE: |
| 3641 | EEC! |
| 364C | BANKC! |
| 3657 | BANKC@ |
| 3664 | BANKEEC! |
| 3674 | BANKEXECUTE |
| 367E | FMTRK |
| 37CF | FORMAT |
| 3805 | ;DUMP |
| 3844 | ADMP |

    Note:
    1. no address for inner interpreter or monitor
    2. address for TASK $40B maybe incorrect
    3. at $2846 the word name is void at listing (PDF)

## FORTH ASM

Table of loaded words

| MEMS | WORD |
| :----: | :----: |
| 38F8 | N | 
| 3903 | IP | 
| 390D | W | 
| 3918 | UP | 
| 3926 | XSAVE | 
| 3933 | NEXT | 
| 3940 | PUSH | 
| 394C | PUT | 
| 3958 | POP | 
| 3967 | POPTWO | 
| 3976 | PUSHOA | 
| 3984 | PUTOA | 
| 3993 | BINARY | 
| 39A1 | SETUP | 
| 39D4 | ,A | 
| 39DF | # | 
| 39EC | MEM | 
| 39F8 | ,X | 
| 3A04 | ,Y | 
| 3A10 | X) | 
| 3A1C | )Y | 
| 3A27 | ) | 
| 3A34 | TOP | 
| 3A44 | SEC | 
| 3A54 | RP) | 
| 3B23 | BRK, | 
| 3B31 | CLC, | 
| 3B3F | CLD, | 
| 3B4D | CLI, | 
| 3B5B | CLV, | 
| 3B69 | DEX, | 
| 3B77 | DEY, | 
| 3B85 | INX, | 
| 3B93 | INY, | 
| 3BA1 | NOP, | 
| 3BAF | PHA, | 
| 3BBD | PHP, | 
| 3BCB | PLA, | 
| 3BD9 | PLP, | 
| 3BE7 | RTI, | 
| 3BF5 | RTS, | 
| 3C03 | SEC, | 
| 3C11 | SED, | 
| 3C1F | SEI, | 
| 3C2D | TAX, | 
| 3C3B | TAY, | 
| 3C49 | TSX, | 
| 3C57 | TXA, | 
| 3C65 | TYA, | 
| 3C73 | TXS, | 
| 3C81 | ADC, | 
| 3C91 | AND, | 
| 3CA1 | CMP, | 
| 3CC1 | LDA, | 
| 3CD1 | ORA, | 
| 3CE1 | SBC, | 
| 3CF1 | STA, | 
| 3D01 | ASL, | 
| 3D11 | DEC, | 
| 3D21 | INC, | 
| 3D31 | LSR, | 
| 3D41 | ROL, | 
| 3D51 | ROR, | 
| 3D61 | STX, | 
| 3D71 | CPX, | 
| 3D91 | LDX, | 
| 3DA1 | LDY, | 
| 3DB1 | CPY, | 
| 3DB1 | EOR, | 
| 3DB1 | STY, | 
| 3DC1 | JSR, | 
| 3DD1 | JMP, | 
| 3DE1 | BIT, | 
| 3DF1 | 8MB, | 
| 3E29 | RMB, | 
| 3E4B | BITSET | 
| 3E79 | BITCLR | 
| 3E99 | BEGIN, | 
| 3EAC | UNTIL, | 
| 3ECD | WHILE, | 
| 3EE6 | AGAIN, | 
| 3EFC | REPEAT, | 
| 3F1E | IF, | 
| 3F37 | ENDIF, | 
| 3F63 | THEN, | 
| 3F73 | ELSE, | 
| 3F9B | NOT | 
| 3FAA | CS | 
| 3FB5 | VS | 
| 3FC0 | 0= | 
| 3FCB | 0< | 
| 3FDC | END-CODE | 

    Note:   Forth reverse order of operands


##

F40E   10 F4                ; CFA
F410   B1 4E                LDA ($4E),Y
F412   48                   PHA
F413   E6 4E                INC $4E
F415   D0 02                BNE LF419
F417   E6 4F                INC $4F
F419   B1 4E      LF419     LDA ($4E),Y
F41B   E6 4E      LF41B     INC $4E
F41D   D0 02                BNE LF421
F41F   E6 4F                INC $4F
F421   CA         LF421     DEX
F422   CA                   DEX
F423   95 01      LF423     STA $01,X
F425   68                   PLA
F426   95 00                STA $00,X

