package taosha.lang

interface ${it.capitalize()}Range {
    interface C {
        void on($it item, int position)
    }

    ${it.capitalize()}Range range(int position, int length)

    $it get(int position)

    int length() 

    void accept(C c)
}

