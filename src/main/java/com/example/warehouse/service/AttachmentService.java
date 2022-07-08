package com.example.warehouse.service;

import org.springframework.stereotype.Service;

@Service
public class AttachmentService {
//    @Autowired
//    AttachmentRepository attachmentRepository;
//    @Autowired
//    AttachmentContentRepository attachmentContentRepository;
//
//    @SneakyThrows
//    public List<UUID> upload(MultipartHttpServletRequest request){
//        Iterator<String> fileNames = request.getFileNames();
//        List<UUID> photoIds = new ArrayList<>();
//        while (fileNames.hasNext()) {
//            String name = fileNames.next();
//            List<MultipartFile> multipartFiles = request.getFiles(name);
//            for (MultipartFile multipartFile : multipartFiles) {
//                Attachment attachment = new Attachment();
//                attachment.setName(multipartFile.getOriginalFilename());
//                attachment.setContentType(multipartFile.getContentType());
//                attachment.setSize(multipartFile.getSize());
//                Attachment save = attachmentRepository.save(attachment);
//
//                AttachmentContent attachmentContent = new AttachmentContent();
//                attachmentContent.setAttachment(save);
//                attachmentContent.setBytes(multipartFile.getBytes());
//                attachmentContentRepository.save(attachmentContent);
//                photoIds.add(save.getId());
//            }
//        }
//        return photoIds;
//    }
//
//    @SneakyThrows
//    public void download(UUID id, HttpServletResponse response){
//        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
//        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(id);
//        if (optionalAttachment.isPresent() && optionalAttachmentContent.isPresent()) {
//            Attachment attachment = optionalAttachment.get();
//            response.setContentType(attachment.getContentType());
//            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "file");
//
//            ServletOutputStream outputStream = response.getOutputStream();
//            outputStream.write(optionalAttachmentContent.get().getBytes());
//        }
//    }
}
