export class Note{
    constructor(
        public id: string,
        public patientId: number,
        public familyName: string,
        public date: Date,
        public description: string){}
}