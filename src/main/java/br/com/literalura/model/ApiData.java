package br.com.literalura.model;

import java.util.List;

public record ApiData(Integer count,
                      String next,
                      String previous,
                      List<BookData> results
                        ) {
}
