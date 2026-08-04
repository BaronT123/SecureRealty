class DocumentModel {
  final int id; final String originalName; final String contentType; final int fileSize; final String uploadedAt;
  const DocumentModel({required this.id,required this.originalName,required this.contentType,required this.fileSize,required this.uploadedAt});
  factory DocumentModel.fromJson(Map<String,dynamic> json)=>DocumentModel(id:(json['id'] as num).toInt(),originalName:json['originalName']?.toString()??'Document',contentType:json['contentType']?.toString()??'',fileSize:(json['fileSize'] as num?)?.toInt()??0,uploadedAt:json['uploadedAt']?.toString()??'');
}
