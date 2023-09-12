# Summary

This project will be mostly coding oriented. I'll be implementing the Twofish cipher in Java. There's not much to talk about regarding this besides turning the paper into a code project.

The end goal hopefully I'll be able to host a demo on my GitHub. At this point, I shall not be too optimistic about hosting Java online. But even if the online one won't work, the bottom line is I'll provide a demo as well as a detailed commented source code. And it should run smoothly locally.

Twofish is a bit more intuitive than AES. Instead of ShiftRow and MixColumn, it uses a maximum distance separable code to ensure a minimum distance within the matrix. And use a fast pseudo-Hadamard transform for confusion.

# Source

Origin Paper:
[Twofish: A 128-Bit Block Cipher by Schnier, Kelsey, et al.](https://www.schneier.com/wp-content/uploads/2016/02/paper-twofish-paper.pdf)

A higher level view of Twofish:
![Structure](https://upload.wikimedia.org/wikipedia/commons/e/ee/Twofishalgo.svg)

# Progress

## Plaintext & key preparation

- [x] plaintext pruning
- [ ] generate round keys (function h)

## Diffusion & confusion

- [ ] build 4 S-box
- [x] implemented function g
- [x] implemented PHT
- [ ] XOR round keys
- [ ] boxing above into one round (function F) and repeating 15 more rounds
- [ ] performance test function F

## Finalize

- [ ] Demo
- [ ] Detailed comments
- [ ] (optional) host demo on github.io
