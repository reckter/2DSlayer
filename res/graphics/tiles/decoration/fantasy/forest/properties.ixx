ixxFile {
    version = 4;
    name = "forest main properties";
}

default {
    name = "main";
}

alternatives {
    length = 1;
    0 {
        name = "extras";
        chance = 0.01;
    }
}