export const schoolManage = {
    schoolManage: {
        self: 'School Management',
        schoolCode: 'School Code',
        schoolName: 'School Name',
        address: 'Address',
        phone: 'Phone Number',
        email: 'Email',
        principal: 'Principal',
        description: 'Description',
        createSchool: 'Add New School',
        updateSchool: 'Update School',
        detail: 'School Details',

        area: 'Area (Ward)',
        areaCvct: 'CVCT Area',
        workingOffsite: 'Workplace',
        schoolType: 'School Level',
        isPremium: 'Premium School',
        programs: 'Math - Science Program',
        programMath: 'Math',
        programScience: 'Science',
        map: 'Map Link',
        minuteNum: 'Attendance Minutes / Actual Minutes',
        rhta: 'RHTA',
        scheduler: 'Scheduler',
        schedulerCumTa: 'Scheduler cum-TA',
        startYear: 'Cooperation Start Academic Year',
        status: 'Status',
        effective: 'Effective',
        inactive: 'Inactive',
        yes: 'Yes',
        no: 'No',

        placeholder: {
            select: 'Select {field}',
            input: 'Enter {field}',
            all: 'All'
        },

        validation: {
            required: '{field} is required',
            max: '{field} must not exceed {max} characters',
            number: '{field} must be a positive integer',
            minOneProgram: 'Please select at least one program',
            length: '{field} must be between {min} and {max} characters long'
        },

        message: {
            addSuccess: 'Successfully added school "{name}"!',
            updateSuccess: 'Successfully updated school "{name}"!',
            deleteSuccess: 'Successfully deleted school "{name}"!',
            saveError: 'An error occurred while saving the school.',
            deleteError: 'An error occurred while deleting the school.',
            loadError: 'Failed to load school list.',
            loadOptionsError: 'Failed to load option data.',
            exportSuccess: 'Excel export successful!',
            exportFailed: 'Excel export failed!',
            exportCancel: 'Export operation canceled.'
        },

        actions: {
            view: 'View',
            edit: 'Edit',
            delete: 'Delete'
        }
    }
};
