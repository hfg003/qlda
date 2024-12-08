package com.dev.qlda.utils;

import com.dev.qlda.repo.AssetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Year;

@RequiredArgsConstructor
@Component
public class CodeGenerators {

    private final AssetRepo assetRepo;

    public String generateAssetCode(String assetType) {
        String yearSuffix = String.valueOf(Year.now().getValue()).substring(2);

        String typeCode = assetType
                .replaceAll("[^a-zA-Z\\s]", "")
                .trim()
                .replaceAll("\\s+", " ")
                .toUpperCase()
                .chars()
                .filter(Character::isUpperCase)
                .mapToObj(c -> String.valueOf((char) c))
                .reduce("", String::concat);

        return yearSuffix + typeCode + "00" + String.format("%03d", assetRepo.count() + 1);
    }

    public String generateAssetCode(String assetType, long number) {
        String yearSuffix = String.valueOf(Year.now().getValue()).substring(2);

        String typeCode = assetType
                .replaceAll("[^a-zA-Z\\s]", "")
                .trim()
                .replaceAll("\\s+", " ")
                .toUpperCase()
                .chars()
                .filter(Character::isUpperCase)
                .mapToObj(c -> String.valueOf((char) c))
                .reduce("", String::concat);

        return yearSuffix + typeCode + "00" + String.format("%03d", number + 1);
    }
}
