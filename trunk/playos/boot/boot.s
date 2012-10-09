section .text
_boot:
    org 0x7c00
    mov ax, cs
    mov ds, ax
    mov es, ax
    call display
display:
    mov ax, MSG
    mov bp, ax
    mov cx, LEN
    mov ax, 01301h
    mov bx, 000ch
    mov dl, 0
    int 10h
    ret
data:
    MSG db "Hello World!", 0xa
    LEN equ $ - MSG
times 510 - ($ - $$) db 0x0
dw 0xaa55
