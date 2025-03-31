package com.language.service.common.utils;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;

public class XmlUtils{
    /**
     * Chuyển đổi XML dạng String sang đối tượng DTO.
     *
     * @param xmlString Chuỗi XML cần chuyển đổi
     * @param dtoClass  Class của DTO tương ứng
     * @param <T>       Kiểu generic của DTO
     * @return Đối tượng DTO
     */
    public static <T> T convertXmlStringToDto(String xmlString, Class<T> dtoClass) {
        try {
            // Tạo context JAXB cho class DTO
            JAXBContext jaxbContext = JAXBContext.newInstance(dtoClass);

            // Tạo Unmarshaller để đọc XML
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Sử dụng StringReader để đọc XML từ chuỗi
            StringReader stringReader = new StringReader(xmlString);

            // Chuyển đổi từ String thành đối tượng DTO
            return (T) unmarshaller.unmarshal(stringReader);
        } catch (JAXBException e) {
            throw new RuntimeException("Error converting XML String to DTO", e);
        }
    }
}
