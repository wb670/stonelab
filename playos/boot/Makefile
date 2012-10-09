OBJS = boot.s

boot.bin:boot.s
	@nasm -o boot.bin boot.s

clean:
	@rm -fr boot.bin boot.img

image:boot.bin
	@dd if=boot.bin of=boot.img bs=512 count=1 
	@rm -fr boot.bin
